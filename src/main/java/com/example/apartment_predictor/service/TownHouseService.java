package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.TownHouse;
 /* POR AHORA NO
import com.example.apartment_predictor.model.Review;
*/
import com.example.apartment_predictor.repository.TownHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TownHouseService {

    @Autowired
    TownHouseRepository townTownHouseRepository;

    public Iterable<TownHouse> findAll() {
        return townTownHouseRepository.findAll();
    }


    /* POR AHORA NO
    public TownHouse createTownHouse(TownHouse townTownHouse){
        if (townTownHouse.getReviews() != null) {
            for (Review r : townTownHouse.getReviews()) {
                r.setTownHouse(townTownHouse);
            }
        }
        return townTownHouseRepository.save(townTownHouse);
    }
    */

    public TownHouse updateTownHouse(TownHouse townTownHouse){
        return townTownHouseRepository.save(townTownHouse);
    }

    /**
     * PUT REAL: Reemplaza completamente el townTownHouse existente por el nuevo.
     */
    public TownHouse updateTownHouseById(String id, TownHouse newTownHouse) {

        Optional<TownHouse> existingOpt = townTownHouseRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        TownHouse existing = existingOpt.get();

         /* POR AHORA NO
        // Limpiar reviews antiguas para evitar duplicados o merges raros
        existing.getReviews().clear();

        // Reasignar reviews al nuevo objeto
        if (newTownHouse.getReviews() != null) {
            for (Review r : newTownHouse.getReviews()) {
                r.setTownHouse(newTownHouse);
            }
        }
        */

        // Mantener el ID original (tu entidad no tiene setId)
        return townTownHouseRepository.save(newTownHouse);
    }

    public void deleteTownHouse(String id){
        townTownHouseRepository.deleteById(id);
    }

    public TownHouse findTownHouseById(String id){
        return townTownHouseRepository.findById(id).orElse(null);
    }



}