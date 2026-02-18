package com.example.apartment_predictor.repository;

import com.example.apartment_predictor.model.TownHouse;
import org.springframework.data.repository.CrudRepository;

public interface TownHouseRepository extends CrudRepository<TownHouse, String> {
}