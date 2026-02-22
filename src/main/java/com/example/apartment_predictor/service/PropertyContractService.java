package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Owner;
import com.example.apartment_predictor.model.Property;
import com.example.apartment_predictor.model.PropertyContract;
import com.example.apartment_predictor.repository.PropertyContractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PropertyContractService {

    private final PropertyContractRepository contractRepository;

    // ============================
    // CONSTRUCTOR
    // ============================
    public PropertyContractService(PropertyContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    // ============================
    // FIND ALL
    // ============================
    public Iterable<PropertyContract> findAll() {
        return contractRepository.findAll();
    }

    // ============================
    // FIND BY ID
    // ============================
    public Optional<PropertyContract> findById(String id) {
        return contractRepository.findById(id);
    }

    // ============================
    // CREATE
    // ============================
    public PropertyContract createContract(Owner owner, Property property, double agreedPrice) {

        PropertyContract contract = new PropertyContract();
        contract.setOwner(owner);
        contract.setProperty(property);
        contract.setAgreedPrice(agreedPrice);
        contract.setStartDate(LocalDate.now());
        contract.setActive(true);

        return contractRepository.save(contract);
    }

    // ============================
    // CLOSE CONTRACT
    // ============================
    public PropertyContract closeContract(String id) {
        Optional<PropertyContract> opt = contractRepository.findById(id);
        if (opt.isEmpty()) return null;

        PropertyContract contract = opt.get();
        contract.setActive(false);
        contract.setEndDate(LocalDate.now());

        return contractRepository.save(contract);
    }

    // ============================
    // DELETE
    // ============================
    public void deleteContract(String id) {
        contractRepository.deleteById(id);
    }
}
