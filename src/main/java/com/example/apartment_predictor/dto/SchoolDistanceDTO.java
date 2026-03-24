package com.example.apartment_predictor.dto;

import com.example.apartment_predictor.model.School;

public record SchoolDistanceDTO(
        School school,
        double haversineMeters,
        double graphMeters
) {}
