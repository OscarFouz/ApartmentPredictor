package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.House;
 /* POR AHORA NO
import com.example.apartment_predictor.model.Review;
*/
import com.example.apartment_predictor.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HouseService {

    @Autowired
    HouseRepository houseRepository;

    public Iterable<House> findAll() {
        return houseRepository.findAll();
    }


    /* POR AHORA NO
    public House createHouse(House house){
        if (house.getReviews() != null) {
            for (Review r : house.getReviews()) {
                r.setHouse(house);
            }
        }
        return houseRepository.save(house);
    }
    */

    public House updateHouse(House house){
        return houseRepository.save(house);
    }

    /**
     * PUT REAL: Reemplaza completamente el house existente por el nuevo.
     */
    public House updateHouseById(String id, House newHouse) {

        Optional<House> existingOpt = houseRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        House existing = existingOpt.get();

         /* POR AHORA NO
        // Limpiar reviews antiguas para evitar duplicados o merges raros
        existing.getReviews().clear();

        // Reasignar reviews al nuevo objeto
        if (newHouse.getReviews() != null) {
            for (Review r : newHouse.getReviews()) {
                r.setHouse(newHouse);
            }
        }
        */

        // Mantener el ID original (tu entidad no tiene setId)
        return houseRepository.save(newHouse);
    }

    public void deleteHouse(String id){
        houseRepository.deleteById(id);
    }

    public House findHouseById(String id){
        return houseRepository.findById(id).orElse(null);
    }



}