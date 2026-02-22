package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.House;
import com.example.apartment_predictor.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/houses")
@CrossOrigin(origins = "http://localhost:5173")
public class HouseController {

    @Autowired
    private HouseService houseService;

    // ============================
    // GET ALL
    // ============================
    @GetMapping
    public ResponseEntity<Iterable<House>> getAll() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "getAllHouses executed");

        return ResponseEntity.ok().headers(headers).body(houseService.findAll());
    }

    // ============================
    // GET BY ID
    // ============================
    @GetMapping("/{id}")
    public ResponseEntity<House> getById(@PathVariable String id) {

        House h = houseService.findHouseById(id);
        return h != null ? ResponseEntity.ok(h) : ResponseEntity.notFound().build();
    }

    // ============================
    // CREATE
    // ============================
    @PostMapping
    public ResponseEntity<House> create(@RequestBody House house) {

        House created = houseService.updateHouse(house);
        return ResponseEntity.ok(created);
    }

    // ============================
    // UPDATE
    // ============================
    @PutMapping("/{id}")
    public ResponseEntity<House> update(@PathVariable String id, @RequestBody House house) {

        House updated = houseService.updateHouseById(id, house);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // ============================
    // DELETE
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        houseService.deleteHouse(id);
        return ResponseEntity.noContent().build();
    }
}
