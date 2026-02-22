package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.PropertyContract;
import com.example.apartment_predictor.service.PropertyContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
@CrossOrigin(origins = "http://localhost:5173")
public class PropertyContractController {

    @Autowired
    private PropertyContractService contractService;

    // ============================
    // GET ALL
    // ============================
    @GetMapping
    public ResponseEntity<Iterable<PropertyContract>> getAll() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "getAllContracts executed");
        headers.add("version", "1.0");
        headers.add("author", "Big");

        return ResponseEntity.ok().headers(headers).body(contractService.findAll());
    }

    // ============================
    // GET BY ID
    // ============================
    @GetMapping("/{id}")
    public ResponseEntity<PropertyContract> getById(@PathVariable String id) {

        return contractService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================
    // CREATE
    // ============================
    @PostMapping
    public ResponseEntity<PropertyContract> create(@RequestBody PropertyContract contract) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "createContract executed");

        if (contract.getOwner() == null || contract.getProperty() == null) {
            headers.add("Error", "Owner or Property missing");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }

        PropertyContract created = contractService.createContract(
                contract.getOwner(),
                contract.getProperty(),
                contract.getAgreedPrice()
        );

        return ResponseEntity.ok().headers(headers).body(created);
    }

    // ============================
    // CLOSE CONTRACT
    // ============================
    @PutMapping("/{id}/close")
    public ResponseEntity<PropertyContract> closeContract(@PathVariable String id) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "closeContract executed");

        PropertyContract closed = contractService.closeContract(id);

        if (closed == null) {
            headers.add("Error", "Contract not found");
            return ResponseEntity.notFound().headers(headers).build();
        }

        return ResponseEntity.ok().headers(headers).body(closed);
    }

    // ============================
    // DELETE
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "deleteContract executed");

        if (contractService.findById(id).isEmpty()) {
            headers.add("Error", "Contract not found");
            return ResponseEntity.notFound().headers(headers).build();
        }

        contractService.deleteContract(id);
        return ResponseEntity.noContent().headers(headers).build();
    }
}
