package dev.mf1.tinyradar.core.oa;

import dev.mf1.tinyradar.core.TinyRadar;
import dev.mf1.tinyradar.core.util.MathUtils;

import java.util.List;

public final class AirportLookup {

    private AirportLookup() {
    }

    public static List<Airport> filterInRange(List<Airport> airports) {
        return airports.stream()
                .filter(a ->
                        MathUtils.inRange(
                                TinyRadar.pos.latitude(),
                                TinyRadar.pos.longitude(),
                                a.getLatitude(),
                                a.getLongitude(),
                                TinyRadar.range
                        ))
                .toList();
    }

}
