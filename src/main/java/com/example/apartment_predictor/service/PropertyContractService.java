package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Owner;
import com.example.apartment_predictor.model.PropertyContract;
import com.example.apartment_predictor.repository.PropertyContractRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PropertyContractService {

    private final PropertyContractRepository contractRepository;

    public PropertyContractService(PropertyContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Iterable<PropertyContract> findAll() {
        return contractRepository.findAll();
    }

    public Optional<PropertyContract> findById(String id) {
        return contractRepository.findById(id);
    }

    public PropertyContract createContract(Owner owner, Apartment apartment, double agreedPrice) {

        PropertyContract contract = new PropertyContract();
        contract.setOwner(owner);
        contract.setApartment(apartment);
        contract.setAgreedPrice(agreedPrice);
        contract.setStartDate(LocalDate.now());
        contract.setActive(true);

        return contractRepository.save(contract);
    }

    public PropertyContract closeContract(String id) {
        Optional<PropertyContract> opt = contractRepository.findById(id);
        if (opt.isEmpty()) return null;

        PropertyContract contract = opt.get();
        contract.setActive(false);
        contract.setEndDate(LocalDate.now());

        return contractRepository.save(contract);
    }

    public void deleteContract(String id) {
        contractRepository.deleteById(id);
    }
}
