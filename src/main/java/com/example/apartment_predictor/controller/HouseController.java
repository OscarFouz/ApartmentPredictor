package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.House;
import com.example.apartment_predictor.service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/houses")
@CrossOrigin(origins = "http://localhost:5173")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public Iterable<House> getAll() {
        return houseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> getById(@PathVariable String id) {
        House h = houseService.findHouseById(id);
        return h != null ? ResponseEntity.ok(h) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public House create(@RequestBody House house) {
        return houseService.updateHouse(house);
    }

    @PutMapping("/{id}")
    public ResponseEntity<House> update(@PathVariable String id, @RequestBody House house) {
        House updated = houseService.updateHouseById(id, house);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        houseService.deleteHouse(id);
        return ResponseEntity.noContent().build();
    }
}
