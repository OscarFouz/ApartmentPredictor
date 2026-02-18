package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Owner;
import com.example.apartment_predictor.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    OwnerRepository townOwnerRepository;

    public Iterable<Owner> findAll() {
        return townOwnerRepository.findAll();
    }

    public Owner updateOwner(Owner townOwner){
        return townOwnerRepository.save(townOwner);
    }

    /**
     * PUT REAL: Reemplaza completamente el townOwner existente por el nuevo.
     */
    public Owner updateOwnerById(String id, Owner newOwner) {

        Optional<Owner> existingOpt = townOwnerRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        Owner existing = existingOpt.get();

        // Mantener el ID original (tu entidad no tiene setId)
        return townOwnerRepository.save(newOwner);
    }

    public void deleteOwner(String id){
        townOwnerRepository.deleteById(id);
    }

    public Owner findOwnerById(String id){
        return townOwnerRepository.findById(id).orElse(null);
    }



}