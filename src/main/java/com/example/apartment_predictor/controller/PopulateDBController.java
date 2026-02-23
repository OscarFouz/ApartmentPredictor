package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.utils.PopulateDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/populate")
@CrossOrigin(origins = "http://localhost:5173")
public class PopulateDBController {

    @Autowired
    private PopulateDB populateDB;

    // ============================
    // POPULATE FULL DATABASE
    // ============================
    @GetMapping
    public ResponseEntity<String> populate(
            @RequestParam(defaultValue = "10") int owners,
            @RequestParam(defaultValue = "50") int properties,
            @RequestParam(defaultValue = "100") int reviews,
            @RequestParam(defaultValue = "20") int schools
    ) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Status", "populate executed");
        headers.add("version", "1.0");
        headers.add("author", "Big");

        // ============================
        // ORCHESTRATOR CALL
        // ============================
        int result = populateDB.populateAll(owners, properties, reviews, schools);

        // ============================
        // CONTROL: BD YA POBLADA
        // ============================
        if (result == -1) {
            headers.add("Error", "Database already populated");
            return ResponseEntity
                    .status(409) // Conflict
                    .headers(headers)
                    .body("Database already populated. Populate aborted.");
        }

        // ============================
        // SUCCESS
        // ============================
        headers.add("Status", "populate success");
        return ResponseEntity.ok()
                .headers(headers)
                .body("Database populated successfully. Total created: " + result);
    }
}
