package dev.mf1.tinyradar.core;

@SuppressWarnings("unused")
public final class Projection {

    private static final double EARTH_RADIUS_MILES = 3958.8;
    private static final double HALF_CIRCUMFERENCE = 20037508.34;

    private Projection() {
    }

    /**
     * Converts geographic coordinates (longitude, latitude) into pixel coordinates.
     *
     * @param lon          Longitude of the point (WGS84).
     * @param lat          Latitude of the point (WGS84).
     * @param centerLon    Longitude of the map's center point.
     * @param centerLat    Latitude of the map's center point.
     * @param distance     Distance from the map center to the screen edge in miles.
     * @param screenWidth  Width of the screen in pixels.
     * @param screenHeight Height of the screen in pixels.
     * @return An array [x, y] representing the pixel coordinates.
     */
    public static int[] project(double lon, double lat, double centerLon, double centerLat,
                                double distance, int screenWidth, int screenHeight) {
        double mapRadiusMeters = distance * 1609.34;
        double pixelsPerMeter = screenWidth / (2 * mapRadiusMeters);

        double[] centerMeters = wgsToMeters(centerLon, centerLat);
        double[] pointMeters = wgsToMeters(lon, lat);

        double dx = pointMeters[0] - centerMeters[0];
        double dy = pointMeters[1] - centerMeters[1];

        int x = (int) (screenWidth / 2 + dx * pixelsPerMeter);
        int y = (int) (screenHeight / 2 - dy * pixelsPerMeter);

        return new int[]{x, y};
    }

    /**
     * Converts pixel coordinates back to geographic coordinates (longitude, latitude).
     *
     * @param x            X pixel coordinate.
     * @param y            Y pixel coordinate.
     * @param centerLon    Longitude of the map's center point.
     * @param centerLat    Latitude of the map's center point.
     * @param distance     Distance from the map center to the screen edge in miles.
     * @param screenWidth  Width of the screen in pixels.
     * @param screenHeight Height of the screen in pixels.
     * @return An array [lon, lat] representing the geographic coordinates (WGS84).
     */
    public static double[] pixelsToLatLon(int x, int y, double centerLon, double centerLat,
                                     double distance, int screenWidth, int screenHeight) {
        double mapRadiusMeters = distance * 1609.34;
        double pixelsPerMeter = screenWidth / (2 * mapRadiusMeters);

        // Find center in meters
        double[] centerMeters = wgsToMeters(centerLon, centerLat);

        // Convert pixels back to meters
        double dx = (x - screenWidth / 2) / pixelsPerMeter;
        double dy = (screenHeight / 2 - y) / pixelsPerMeter;

        // Calculate point meters
        double pointX = centerMeters[0] + dx;
        double pointY = centerMeters[1] + dy;

        // Convert meters back to WGS84
        return metersToWgs(pointX, pointY);
    }

    /**
     * Converts WGS84 coordinates (longitude, latitude) to Web Mercator meters.
     *
     * @param lon Longitude (WGS84).
     * @param lat Latitude (WGS84).
     * @return An array [x, y] representing the coordinates in meters.
     */
    private static double[] wgsToMeters(double lon, double lat) {
        double x = lon * HALF_CIRCUMFERENCE / 180.0;
        double y = Math.log(Math.tan((90.0 + lat) * Math.PI / 360.0)) / (Math.PI / 180.0);
        y = y * HALF_CIRCUMFERENCE / 180.0;
        return new double[]{x, y};
    }

    /**
     * Converts Web Mercator meters to WGS84 coordinates (longitude, latitude).
     *
     * @param x X coordinate in meters.
     * @param y Y coordinate in meters.
     * @return An array [lon, lat] representing the geographic coordinates (WGS84).
     */
    private static double[] metersToWgs(double x, double y) {
        double lon = x * 180.0 / HALF_CIRCUMFERENCE;
        double lat = y * 180.0 / HALF_CIRCUMFERENCE;
        lat = 180.0 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180.0)) - Math.PI / 2);
        return new double[]{lon, lat};
    }

}
