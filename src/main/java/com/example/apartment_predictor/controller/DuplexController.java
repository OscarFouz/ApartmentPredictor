package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.model.Duplex;
import com.example.apartment_predictor.service.DuplexService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/duplexes")
@CrossOrigin(origins = "http://localhost:5173")
public class DuplexController {

    private final DuplexService duplexService;

    public DuplexController(DuplexService duplexService) {
        this.duplexService = duplexService;
    }

    @GetMapping
    public Iterable<Duplex> getAll() {
        return duplexService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Duplex> getById(@PathVariable String id) {
        Duplex d = duplexService.findDuplexById(id);
        return d != null ? ResponseEntity.ok(d) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Duplex create(@RequestBody Duplex duplex) {
        return duplexService.updateDuplex(duplex);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Duplex> update(@PathVariable String id, @RequestBody Duplex duplex) {
        Duplex updated = duplexService.updateDuplexById(id, duplex);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        duplexService.deleteDuplex(id);
        return ResponseEntity.noContent().build();
    }
}
