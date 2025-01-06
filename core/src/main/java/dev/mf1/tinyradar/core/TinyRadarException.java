package dev.mf1.tinyradar.core;

public class TinyRadarException extends RuntimeException {

    public TinyRadarException(Throwable cause) {
        super(cause);
    }

    public TinyRadarException(String message) {
        super(message);
    }

}
