package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Duplex;
 /* POR AHORA NO
import com.example.apartment_predictor.model.Review;
*/
import com.example.apartment_predictor.repository.DuplexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DuplexService {

    @Autowired
    DuplexRepository duplexRepository;

    public Iterable<Duplex> findAll() {
        return duplexRepository.findAll();
    }


    /* POR AHORA NO
    public Duplex createDuplex(Duplex duplex){
        if (duplex.getReviews() != null) {
            for (Review r : duplex.getReviews()) {
                r.setDuplex(duplex);
            }
        }
        return duplexRepository.save(duplex);
    }
    */

    public Duplex updateDuplex(Duplex duplex){
        return duplexRepository.save(duplex);
    }

    /**
     * PUT REAL: Reemplaza completamente el duplex existente por el nuevo.
     */
    public Duplex updateDuplexById(String id, Duplex newDuplex) {

        Optional<Duplex> existingOpt = duplexRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        Duplex existing = existingOpt.get();

         /* POR AHORA NO
        // Limpiar reviews antiguas para evitar duplicados o merges raros
        existing.getReviews().clear();

        // Reasignar reviews al nuevo objeto
        if (newDuplex.getReviews() != null) {
            for (Review r : newDuplex.getReviews()) {
                r.setDuplex(newDuplex);
            }
        }
        */

        // Mantener el ID original (tu entidad no tiene setId)
        return duplexRepository.save(newDuplex);
    }

    public void deleteDuplex(String id){
        duplexRepository.deleteById(id);
    }

    public Duplex findDuplexById(String id){
        return duplexRepository.findById(id).orElse(null);
    }



}