package com.example.apartment_predictor.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.apartment_predictor.model.Duplex;

public interface DuplexRepository extends CrudRepository <Duplex,String> {
}
