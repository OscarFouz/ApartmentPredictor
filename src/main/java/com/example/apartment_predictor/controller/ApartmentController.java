package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.SchoolRepository;
import com.example.apartment_predictor.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Apartments", description = "Apartment management endpoints")
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Operation(summary = "Get all apartments", description = "Retrieves all apartments from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping
    public ResponseEntity<Iterable<Apartment>> getAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "getAllApartments executed");
        headers.add("version", "2.0");
        headers.add("active", "true");
        headers.add("author", "Oscar");
        return ResponseEntity.ok().headers(headers).body(apartmentService.findAll());
    }

    @Operation(summary = "Get apartment by ID", description = "Retrieves a specific apartment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartment found"),
            @ApiResponse(responseCode = "404", description = "Apartment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getById(
            @Parameter(description = "Apartment ID") @PathVariable String id) {
        Apartment apt = apartmentService.findApartmentById(id);
        return apt != null ? ResponseEntity.ok(apt) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create apartment", description = "Creates a new apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartment created successfully")
    })
    @PostMapping
    public ResponseEntity<Apartment> create(@RequestBody Apartment apartment) {
        Apartment created = apartmentService.createApartment(apartment);
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Update apartment", description = "Updates an existing apartment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Apartment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Apartment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Apartment> update(
            @Parameter(description = "Apartment ID") @PathVariable String id,
            @RequestBody Apartment apartment) {
        Apartment updated = apartmentService.updateApartmentById(id, apartment);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete apartment", description = "Deletes an apartment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Apartment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Apartment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Apartment ID") @PathVariable String id) {
        Apartment apt = apartmentService.findApartmentById(id);
        if (apt == null) return ResponseEntity.notFound().build();
        apartmentService.deleteApartment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Assign schools to apartment", description = "Assigns multiple schools to an apartment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Schools assigned successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request - apartment or schools not found")
    })
    @PutMapping("/{id}/assign-schools")
    public ResponseEntity<Apartment> assignSchools(
            @Parameter(description = "Apartment ID") @PathVariable String id,
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
