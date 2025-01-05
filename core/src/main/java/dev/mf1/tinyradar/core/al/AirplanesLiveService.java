package dev.mf1.tinyradar.core.al;

import dev.mf1.tinyradar.core.Filter;
import dev.mf1.tinyradar.core.WGS84;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class AirplanesLiveService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final AirplanesLiveConfig config = ConfigFactory.create(AirplanesLiveConfig.class);

    public String get(WGS84 wgs84, float radius) {
        String url = config.getPoint(
                String.valueOf(wgs84.latitude()),
                String.valueOf(wgs84.longitude()),
                String.valueOf(radius)
        );

        if (Filter.showMilOnly) {
            url = config.getMil();
        }

        log.info("requesting {}", url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
