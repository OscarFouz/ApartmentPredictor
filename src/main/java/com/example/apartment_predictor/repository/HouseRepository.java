package com.example.apartment_predictor.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.apartment_predictor.model.House;

public interface HouseRepository extends CrudRepository <House, String> {
}
