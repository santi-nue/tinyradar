package dev.mf1.tinyradar.gui;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public final class Resources {

    private Resources() { }

    public static InputStream getAsStream(String path) {
        return Objects.requireNonNull(
                Resources.class.getResourceAsStream(path), "Resource not found: " + path
        );
    }

    public static URL get(String path) {
        return Resources.class.getResource(path);
    }

}
