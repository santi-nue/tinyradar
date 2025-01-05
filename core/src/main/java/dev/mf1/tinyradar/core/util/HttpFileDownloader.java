package dev.mf1.tinyradar.core.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

@Slf4j
public final class HttpFileDownloader {

    private static final HttpClient client = HttpClient.newHttpClient();

    private HttpFileDownloader() {
    }

    public static CompletableFuture<HttpResponse<Path>> asyncDownload(String url, Path target) {
        log.debug("Downloading {}", url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofFile(target));
    }

}
