package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.SchoolRepository;
import com.example.apartment_predictor.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/apartments")
@CrossOrigin(origins = "http://localhost:5173")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private SchoolRepository schoolRepository;

    // ============================
    // GET ALL
    // ============================
    @GetMapping
    public ResponseEntity<Iterable<Apartment>> getAll() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "getAllApartments executed");
        headers.add("version", "2.0");
        headers.add("active", "true");
        headers.add("author", "Oscar");

        return ResponseEntity.ok().headers(headers).body(apartmentService.findAll());
    }

    // ============================
    // GET BY ID
    // ============================
    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getById(@PathVariable String id) {

        Apartment apt = apartmentService.findApartmentById(id);
        return apt != null ? ResponseEntity.ok(apt) : ResponseEntity.notFound().build();
    }

    // ============================
    // CREATE
    // ============================
    @PostMapping
    public ResponseEntity<Apartment> create(@RequestBody Apartment apartment) {

        Apartment created = apartmentService.createApartment(apartment);
        return ResponseEntity.ok(created);
    }

    // ============================
    // UPDATE
    // ============================
    @PutMapping("/{id}")
    public ResponseEntity<Apartment> update(
            @PathVariable String id,
            @RequestBody Apartment apartment) {

        Apartment updated = apartmentService.updateApartmentById(id, apartment);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // ============================
    // DELETE
    // ============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {

        Apartment apt = apartmentService.findApartmentById(id);
        if (apt == null) return ResponseEntity.notFound().build();

        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }

    // ============================
    // ASSIGN SCHOOLS
    // ============================
    @PutMapping("/{id}/assign-schools")
    public ResponseEntity<Apartment> assignSchools(
            @PathVariable String id,
            @RequestBody List<String> schoolIds) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "assignSchools executed");
        headers.add("version", "2.0");
        headers.add("author", "Oscar");

        Apartment apartment = apartmentService.findApartmentById(id);
        if (apartment == null) {
            headers.add("Error", "Apartment not found");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }

        Iterable<School> found = schoolRepository.findAllById(schoolIds);
        List<School> schoolsFound = StreamSupport.stream(found.spliterator(), false)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (schoolsFound.size() != schoolIds.size()) {
            headers.add("Error", "Some schools not found");
            return ResponseEntity.badRequest().headers(headers).body(null);
        }

        apartment.setNearbySchools(schoolsFound);
        Apartment saved = apartmentService.updateApartment(apartment);

        headers.add("Status", "assignSchools success");
        return ResponseEntity.ok().headers(headers).body(saved);
    }
}
