package com.example.apartment_predictor.service;

import com.example.apartment_predictor.dto.SchoolDistanceDTO;
import com.example.apartment_predictor.graph.ManhattanGraphService;
import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Duplex;
import com.example.apartment_predictor.model.House;
import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.ApartmentRepository;
import com.example.apartment_predictor.repository.DuplexRepository;
import com.example.apartment_predictor.repository.HouseRepository;
import com.example.apartment_predictor.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class DistanceService {

    @Autowired
    private HaversineService haversineService;

    @Autowired
    private ManhattanGraphService manhattanGraphService;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private DuplexRepository duplexRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    private Object findProperty(String id) {
        if (apartmentRepository.existsById(id)) return apartmentRepository.findById(id).get();
        if (houseRepository.existsById(id)) return houseRepository.findById(id).get();
        if (duplexRepository.existsById(id)) return duplexRepository.findById(id).get();
        throw new RuntimeException("Property not found: " + id);
    }

    private double getLat(Object property) {
        if (property instanceof Apartment a) return a.getLatitude();
        if (property instanceof House h) return h.getLatitude();
        if (property instanceof Duplex d) return d.getLatitude();
        throw new RuntimeException("Unknown property type");
    }

    private double getLon(Object property) {
        if (property instanceof Apartment a) return a.getLongitude();
        if (property instanceof House h) return h.getLongitude();
        if (property instanceof Duplex d) return d.getLongitude();
        throw new RuntimeException("Unknown property type");
    }

    public List<SchoolDistanceDTO> getSchoolsWithDistances(String propertyId) {

        Object property = findProperty(propertyId);

        double lat = getLat(property);
        double lon = getLon(property);

        List<School> schools = schoolRepository.findAll();

        return schools.stream()
                .map(school -> new SchoolDistanceDTO(
                        school,
                        haversineService.calculateDistance(
                                lat, lon,
                                school.getLatitude(), school.getLongitude()
                        ),
                        manhattanGraphService.calculateDistance(
                                lat, lon,
                                school.getLatitude(), school.getLongitude()
                        )
                ))
                .sorted(Comparator.comparingDouble(SchoolDistanceDTO::haversineMeters))
                .toList();
    }
}
