package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.TownHouse;
import com.example.apartment_predictor.repository.TownHouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TownHouseService {

    private final TownHouseRepository townHouseRepository;

    // ============================
    // CONSTRUCTOR
    // ============================
    public TownHouseService(TownHouseRepository townHouseRepository) {
        this.townHouseRepository = townHouseRepository;
    }

    // ============================
    // FIND ALL
    // ============================
    public Iterable<TownHouse> findAll() {
        return townHouseRepository.findAll();
    }

    // ============================
    // UPDATE DIRECTO
    // ============================
    public TownHouse updateTownHouse(TownHouse townHouse){
        return townHouseRepository.save(townHouse);
    }

    // ============================
    // UPDATE POR ID
    // ============================
    public TownHouse updateTownHouseById(String id, TownHouse newTownHouse) {
        Optional<TownHouse> existingOpt = townHouseRepository.findById(id);
        if (existingOpt.isEmpty()) return null;
        return townHouseRepository.save(newTownHouse);
    }

    // ============================
    // DELETE
    // ============================
    public void deleteTownHouse(String id){
        townHouseRepository.deleteById(id);
    }

    // ============================
    // FIND BY ID
    // ============================
    public TownHouse findTownHouseById(String id){
        return townHouseRepository.findById(id).orElse(null);
    }
}
