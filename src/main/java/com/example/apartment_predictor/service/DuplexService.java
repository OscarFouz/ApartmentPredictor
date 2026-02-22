package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Duplex;
import com.example.apartment_predictor.repository.DuplexRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DuplexService {

    private final DuplexRepository duplexRepository;

    // ============================
    // CONSTRUCTOR
    // ============================
    public DuplexService(DuplexRepository duplexRepository) {
        this.duplexRepository = duplexRepository;
    }

    // ============================
    // FIND ALL
    // ============================
    public Iterable<Duplex> findAll() {
        return duplexRepository.findAll();
    }

    // ============================
    // UPDATE DIRECTO
    // ============================
    public Duplex updateDuplex(Duplex duplex){
        return duplexRepository.save(duplex);
    }

    // ============================
    // UPDATE POR ID
    // ============================
    public Duplex updateDuplexById(String id, Duplex newDuplex) {
        Optional<Duplex> existingOpt = duplexRepository.findById(id);
        if (existingOpt.isEmpty()) return null;
        return duplexRepository.save(newDuplex);
    }

    // ============================
    // DELETE
    // ============================
    public void deleteDuplex(String id){
        duplexRepository.deleteById(id);
    }

    // ============================
    // FIND BY ID
    // ============================
    public Duplex findDuplexById(String id){
        return duplexRepository.findById(id).orElse(null);
    }
}
