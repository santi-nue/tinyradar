package dev.mf1.tinyradar.core.util;

public final class MathUtils {

    // Earth radius in km
    private static final int EARTH_RADIUS = 6371;

    private MathUtils() {
    }

    @SuppressWarnings("unused")
    public static float[] calculateBoundingBox(float latitude, float longitude, float radiusKm) {
        float delta = radiusKm / EARTH_RADIUS;

        float latMin = latitude - (float) Math.toDegrees(delta);
        float latMax = latitude + (float) Math.toDegrees(delta);

        float deltaLon = (float) Math.toDegrees(delta / Math.cos(Math.toRadians(latitude)));
        float lonMin = longitude - deltaLon;
        float lonMax = longitude + deltaLon;

        return new float[]{latMin, lonMin, latMax, lonMax};
    }

    /**
     * Checks if the distance between two geographical coordinates is within a specified radius.
     *
     * @param lat1 Latitude of the first point.
     * @param lon1 Longitude of the first point.
     * @param lat2 Latitude of the second point.
     * @param lon2 Longitude of the second point.
     * @param radiusInMiles Radius in miles to check the distance within.
     * @return {@code true} if the distance between the two points is less than or equal to the radius, {@code false} otherwise.
     */
    public static boolean inRange(double lat1, double lon1, double lat2, double lon2, double radiusInMiles) {
        final double EARTH_RADIUS_MILES = 3958.8;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_MILES * c;
        return distance <= radiusInMiles;
    }

}
