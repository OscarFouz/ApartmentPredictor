package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Owner;
import com.example.apartment_predictor.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    // ============================
    // CONSTRUCTOR
    // ============================
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    // ============================
    // FIND ALL
    // ============================
    public Iterable<Owner> findAll() {
        return ownerRepository.findAll();
    }

    // ============================
    // UPDATE DIRECTO
    // ============================
    public Owner updateOwner(Owner owner){
        return ownerRepository.save(owner);
    }

    // ============================
    // UPDATE POR ID
    // ============================
    public Owner updateOwnerById(String id, Owner newOwner) {
        Optional<Owner> existingOpt = ownerRepository.findById(id);
        if (existingOpt.isEmpty()) return null;
        return ownerRepository.save(newOwner);
    }

    // ============================
    // DELETE
    // ============================
    public void deleteOwner(String id){
        ownerRepository.deleteById(id);
    }

    // ============================
    // FIND BY ID
    // ============================
    public Owner findOwnerById(String id){
        return ownerRepository.findById(id).orElse(null);
    }
}
