package dev.mf1.tinyradar.core.event;

import dev.mf1.tinyradar.core.al.Aircraft;

import java.util.List;

public record FlightsUpdateEvent(List<Aircraft> flights) {
}
