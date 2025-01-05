package dev.mf1.tinyradar.core.event;

import dev.mf1.tinyradar.core.WGS84;

public record LocationChangeEvent(WGS84 newLocation) {
}
