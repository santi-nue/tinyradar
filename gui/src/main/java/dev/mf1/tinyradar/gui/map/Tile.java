package dev.mf1.tinyradar.gui.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.awt.image.BufferedImage;
import java.util.Objects;

@RequiredArgsConstructor
@ToString
@Getter
public class Tile {

    private final int x;
    private final int y;
    private final int zoom;

    @Setter
    private BufferedImage image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return x == tile.x && y == tile.y && zoom == tile.zoom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, zoom);
    }

    public String toFilename() {
        return "%d-%d-%d.png".formatted(zoom, x, y);
    }

    public String toUrl() {
        return "https://a.basemaps.cartocdn.com/dark_all/%d/%d/%d.png".formatted(zoom, x, y);
    }

}
