package dev.mf1.tinyradar.gui.map;

import dev.mf1.tinyradar.core.WGS84;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class MapUtils {

    private static final double EARTH_CIRCUMFERENCE = 40075016.686;
    private static final int TILE_SIZE = 256;

    private MapUtils() {
    }

    /**
     * Calculates the range of tiles required to cover the map view based on the center position,
     * viewport size, and zoom level, and returns the corresponding list of tiles.
     *
     * @param position position (WGS84) of the map
     * @param width width of the viewport in pixels
     * @param height height of the viewport in pixels
     * @param zoom zoom level of the map
     * @return a list of tiles covering the viewport
     */
    public static List<Tile> fetchTiles(WGS84 position, int width, int height, int zoom) {
        List<Tile> tiles = new ArrayList<>();

        double centerTileX = lonToTileX(position.longitude(), zoom);
        double centerTileY = latToTileY(position.latitude(), zoom);

        int halfTilesX = (int) Math.ceil((double) width / TILE_SIZE / 2);
        int halfTilesY = (int) Math.ceil((double) height / TILE_SIZE / 2);

        int minTileX = Math.max(0, (int) centerTileX - halfTilesX);
        int maxTileX = Math.min((1 << zoom) - 1, (int) centerTileX + halfTilesX);
        int minTileY = Math.max(0, (int) centerTileY - halfTilesY);
        int maxTileY = Math.min((1 << zoom) - 1, (int) centerTileY + halfTilesY);

        for (int x = minTileX; x <= maxTileX; x++) {
            for (int y = minTileY; y <= maxTileY; y++) {
                tiles.add(new Tile(x, y, zoom));
            }
        }

        return tiles;
    }

    /**
     * Converts a geographic position (WGS84) to screen coordinates relative to the map viewport.
     *
     * @param pos position (WGS84) to convert
     * @param zoom zoom level of the map
     * @param center central geographic position (WGS84) of the map
     * @param width width of the map viewport in pixels
     * @param height height of the map viewport in pixels
     * @return an array {x, y} representing the screen coordinates of the position
     */
    public static int[] getScreenPosition(WGS84 pos, int zoom, WGS84 center, int width, int height) {
        int n = 1 << zoom;

        double lonFactor = TILE_SIZE * n / 360.0;
        double latFactor = TILE_SIZE * n / (2 * Math.PI);

        // Object pixel coordinates
        double pixelX = (pos.longitude() + 180) * lonFactor;
        double pixelY = latFactor * (Math.PI - Math.log(Math.tan(Math.toRadians(pos.latitude())) + 1 / Math.cos(Math.toRadians(pos.latitude()))));

        // Center pixel coordinates
        double centerPixelX = (center.longitude() + 180) * lonFactor;
        double centerPixelY = latFactor * (Math.PI - Math.log(Math.tan(Math.toRadians(center.latitude())) + 1 / Math.cos(Math.toRadians(center.latitude()))));

        // Offset relative to the map center
        int screenX = (int) (pixelX - centerPixelX + width / 2);
        int screenY = (int) (pixelY - centerPixelY + height / 2);

        return new int[]{screenX, screenY};
    }

    /**
     * Converts longitude to the horizontal tile index at a given zoom level.
     *
     * @param lon longitude in degrees
     * @param zoom zoom level
     * @return horizontal tile index
     */
    private static double lonToTileX(double lon, int zoom) {
        return ((lon + 180.0) / 360.0) * (1 << zoom);
    }

    /**
     * Converts latitude to the vertical tile index at a given zoom level.
     *
     * @param lat latitude in degrees
     * @param zoom zoom level
     * @return vertical tile index
     */
    private static double latToTileY(double lat, int zoom) {
        double sinLat = Math.sin(Math.toRadians(lat));
        return ((1 - Math.log((1 + sinLat) / (1 - sinLat)) / (2 * Math.PI)) / 2) * (1 << zoom);
    }

    /**
     * Calculates the center tile's coordinates from a list of tiles.
     *
     * @param tiles the list of tiles
     * @return a Point representing the center tile's coordinates
     */
    public static Point getCenterTile(List<Tile> tiles) {
        int sumX = 0, sumY = 0;
        for (Tile tile : tiles) {
            sumX += tile.getX();
            sumY += tile.getY();
        }

        int centerTileX = sumX / tiles.size();
        int centerTileY = sumY / tiles.size();

        return new Point(centerTileX, centerTileY);
    }

    /**
     * Calculates the pixel position within a tile for a given latitude and longitude.
     *
     * @param pos position (WGS84) to convert
     * @param zoom zoom level
     * @return an array {x, y} representing the pixel position inside the tile
     */
    public static int[] getPointInTile(WGS84 pos, int zoom) {
        int n = 1 << zoom;

        double lonFactor = TILE_SIZE * n / 360.0;
        double latFactor = TILE_SIZE * n / (2 * Math.PI);

        double pixelX = (pos.longitude() + 180) * lonFactor;
        double sinLat = Math.sin(Math.toRadians(pos.latitude()));
        double pixelY = latFactor * (Math.PI - Math.log((1 + sinLat) / (1 - sinLat)) / 2);

        int tilePixelX = (int) (pixelX % TILE_SIZE);
        int tilePixelY = (int) (pixelY % TILE_SIZE);

        return new int[]{tilePixelX, tilePixelY};
    }

    /**
     * Calculates the radius of the visible map area in nautical miles.
     *
     * @param latitude latitude at the map center in degrees
     * @param zoom zoom level
     * @param width map width in pixels
     * @param height map height in pixels
     * @return map radius in nautical miles
     */
    public static double calculateMapRadius(double latitude, int zoom, int width, int height) {
        int n = 1 << zoom;

        double tileResolution = EARTH_CIRCUMFERENCE / (TILE_SIZE * n);
        double metersPerPixel = tileResolution * Math.cos(Math.toRadians(latitude));

        int radiusInPixels = Math.max(width, height) / 2;
        double radiusInMeters = radiusInPixels * metersPerPixel;
        return radiusInMeters / 1852.0;
    }

}
