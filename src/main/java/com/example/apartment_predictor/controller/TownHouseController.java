package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.TownHouse;
import com.example.apartment_predictor.service.TownHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/townhouses")
@CrossOrigin(origins = "http://localhost:5173")
public class TownHouseController {

    @Autowired
    private TownHouseService townHouseService;

    // ============================
    // GET ALL
    // ============================
    @GetMapping
    public Iterable<TownHouse> getAll() {
        return townHouseService.findAll();
    }

    // ============================
    // GET BY ID
    // ============================
    @GetMapping("/{id}")
    public ResponseEntity<TownHouse> getById(@PathVariable String id) {

        TownHouse t = townHouseService.findTownHouseById(id);
        return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build();
    }

    // ============================
    // CREATE
    // ============================
    @PostMapping
    public TownHouse create(@RequestBody TownHouse townHouse) {
        return townHouseService.updateTownHouse(townHouse);
    }

    // ============================
    // UPDATE
    // ============================
    @PutMapping("/{id}")
    public ResponseEntity<TownHouse> update(@PathVariable String id, @RequestBody TownHouse townHouse) {

        TownHouse updated = townHouseService.updateTownHouseById(id, townHouse);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // ============================
    // DELETE
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        townHouseService.deleteTownHouse(id);
        return ResponseEntity.noContent().build();
    }
}
