package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schools")
@CrossOrigin(origins = "http://localhost:5173")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public Iterable<School> getAll() {
        return schoolService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getById(@PathVariable String id) {
        School s = schoolService.findById(id);
        return s != null ? ResponseEntity.ok(s) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public School create(@RequestBody School school) {
        return schoolService.save(school);
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> update(@PathVariable String id, @RequestBody School school) {
        School updated = schoolService.updateById(id, school);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        schoolService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
