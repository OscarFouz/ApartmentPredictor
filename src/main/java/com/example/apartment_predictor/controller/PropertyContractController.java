package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.PropertyContract;
import com.example.apartment_predictor.service.PropertyContractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin(origins = "http://localhost:5173")
public class PropertyContractController {

    private final PropertyContractService contractService;

    public PropertyContractController(PropertyContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public Iterable<PropertyContract> getAll() {
        return contractService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyContract> getById(@PathVariable String id) {
        return contractService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PropertyContract create(@RequestBody PropertyContract contract) {
        return contractService.createContract(
                contract.getOwner(),
                contract.getApartment(),
                contract.getAgreedPrice()
        );
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<PropertyContract> close(@PathVariable String id) {
        PropertyContract closed = contractService.closeContract(id);
        return closed != null ? ResponseEntity.ok(closed) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}
