package dev.mf1.tinyradar.core.oa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.mf1.tinyradar.core.TinyRadarException;
import lombok.Data;

@Data
@JsonDeserialize(using = AirportDeserializer.class)
public class Airport {

    private String id;
    private String ident;
    private Type type;
    private String name;
    private double latitude;
    private double longitude;
    private int elevation;

    public enum Type {

        CLOSED,
        HELIPORT,
        LARGE,
        MEDIUM,
        SEAPLANE,
        BALLOONPORT,
        SMALL;

        public static Type of(String value) {
            return switch (value) {
                case "closed" -> CLOSED;
                case "heliport" -> HELIPORT;
                case "large_airport" -> LARGE;
                case "medium_airport" -> MEDIUM;
                case "seaplane_base" -> SEAPLANE;
                case "small_airport" -> SMALL;
                case "balloonport" -> BALLOONPORT;
                default -> throw new IllegalArgumentException("Unknown airport type: " + value);
            };
        }

    }

}
