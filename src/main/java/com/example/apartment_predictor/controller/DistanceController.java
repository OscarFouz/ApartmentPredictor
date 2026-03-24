package com.example.apartment_predictor.controller;

import com.example.apartment_predictor.dto.SchoolDistanceDTO;
import com.example.apartment_predictor.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/distance")
@CrossOrigin(origins = "*")
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    @GetMapping("/schools")
    public List<SchoolDistanceDTO> getSchoolsWithDistances(
            @RequestParam String propertyId
    ) {
        return distanceService.getSchoolsWithDistances(propertyId);
    }
}
