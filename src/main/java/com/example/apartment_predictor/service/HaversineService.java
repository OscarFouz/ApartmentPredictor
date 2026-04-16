package com.example.apartment_predictor.service;

import com.example.apartment_predictor.utils.DistanceCalculator;
import org.springframework.stereotype.Service;

@Service
public class HaversineService {

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return DistanceCalculator.haversine(lat1, lon1, lat2, lon2);
    }
}
