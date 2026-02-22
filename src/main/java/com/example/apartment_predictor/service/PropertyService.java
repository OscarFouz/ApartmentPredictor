package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Property;
import com.example.apartment_predictor.repository.ApartmentRepository;
import com.example.apartment_predictor.repository.DuplexRepository;
import com.example.apartment_predictor.repository.HouseRepository;
import com.example.apartment_predictor.repository.TownHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private ApartmentRepository apartmentRepo;
    @Autowired private HouseRepository houseRepo;
    @Autowired private DuplexRepository duplexRepo;
    @Autowired private TownHouseRepository townRepo;

    public Property findById(String id) {
        return apartmentRepo.findById(id)
                .map(p -> (Property)p)
                .or(() -> houseRepo.findById(id).map(p -> (Property)p))
                .or(() -> duplexRepo.findById(id).map(p -> (Property)p))
                .or(() -> townRepo.findById(id).map(p -> (Property)p))
                .orElse(null);
    }
}
