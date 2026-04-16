package com.example.apartment_predictor.exception;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException(String id) {
        super("Property not found: " + id);
    }
}
