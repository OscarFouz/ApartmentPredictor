package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.*;
import com.example.apartment_predictor.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
@CrossOrigin(origins = "http://localhost:5173")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public Iterable<Owner> getAll() {
        return ownerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getById(@PathVariable String id) {
        Owner o = ownerService.findOwnerById(id);
        return o != null ? ResponseEntity.ok(o) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Owner create(@RequestBody Owner owner) {
        return ownerService.updateOwner(owner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> update(@PathVariable String id, @RequestBody Owner owner) {
        Owner updated = ownerService.updateOwnerById(id, owner);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }

    // ============================
    // ENDPOINTS DE RELACIONES
    // ============================

    @GetMapping("/{id}/houses")
    public ResponseEntity<?> getOwnerHouses(@PathVariable String id) {
        Owner owner = ownerService.findOwnerById(id);
        return owner != null ? ResponseEntity.ok(owner.getHouses()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/duplexes")
    public ResponseEntity<?> getOwnerDuplexes(@PathVariable String id) {
        Owner owner = ownerService.findOwnerById(id);
        return owner != null ? ResponseEntity.ok(owner.getDuplexes()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/townhouses")
    public ResponseEntity<?> getOwnerTownHouses(@PathVariable String id) {
        Owner owner = ownerService.findOwnerById(id);
        return owner != null ? ResponseEntity.ok(owner.getTownHouses()) : ResponseEntity.notFound().build();
    }
}
