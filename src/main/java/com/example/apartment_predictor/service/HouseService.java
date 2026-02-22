package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.House;
import com.example.apartment_predictor.repository.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    // ============================
    // CONSTRUCTOR
    // ============================
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    // ============================
    // FIND ALL
    // ============================
    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }

    // ============================
    // UPDATE DIRECTO
    // ============================
    public House updateHouse(House house){
        return houseRepository.save(house);
    }

    // ============================
    // UPDATE POR ID
    // ============================
    public House updateHouseById(String id, House newHouse) {
        return houseRepository.findById(id).map(existing -> {

            existing.setName(newHouse.getName());
            existing.setAddress(newHouse.getAddress());
            existing.setOwner(newHouse.getOwner());
            existing.setNearbySchools(newHouse.getNearbySchools());

            return houseRepository.save(existing);

        }).orElse(null);
    }


    // ============================
    // DELETE
    // ============================
    public void deleteHouse(String id){
        houseRepository.deleteById(id);
    }

    // ============================
    // FIND BY ID
    // ============================
    public House findHouseById(String id){
        return houseRepository.findById(id).orElse(null);
    }
}
