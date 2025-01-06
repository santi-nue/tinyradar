package dev.mf1.tinyradar.core.ps;

import dev.mf1.tinyradar.core.TinyRadarException;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class PlaneSpotterService {

    private final HttpClient client = HttpClient.newHttpClient();
    private final PlaneSpotterConfig config = ConfigFactory.create(PlaneSpotterConfig.class);

    public String get(String reg) {
        String url = config.latestPhotoByReg(reg);

        log.info("requesting {}", url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new TinyRadarException(e);
        }
    }

}
