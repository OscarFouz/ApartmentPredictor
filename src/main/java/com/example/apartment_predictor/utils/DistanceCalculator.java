package com.example.apartment_predictor.utils;

/**
 * Utility class for calculating distances between geographic coordinates.
 * Uses the Haversine formula to calculate the great-circle distance
 * between two points on a sphere given their latitude and longitude.
 *
 * @author Oscar
 * @version 1.0.0
 */
public final class DistanceCalculator {

    private static final double EARTH_RADIUS_METERS = 6371e3;

    private DistanceCalculator() {}

    /**
     * Calculates the distance between two geographic coordinates using the Haversine formula.
     *
     * @param lat1 Latitude of the first point in degrees
     * @param lon1 Longitude of the first point in degrees
     * @param lat2 Latitude of the second point in degrees
     * @param lon2 Longitude of the second point in degrees
     * @return Distance between the two points in meters
     */
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        return EARTH_RADIUS_METERS * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
