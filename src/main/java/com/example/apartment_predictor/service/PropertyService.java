package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Duplex;
import com.example.apartment_predictor.model.House;
import com.example.apartment_predictor.model.Property;
import com.example.apartment_predictor.model.TownHouse;
import com.example.apartment_predictor.repository.ApartmentRepository;
import com.example.apartment_predictor.repository.DuplexRepository;
import com.example.apartment_predictor.repository.HouseRepository;
import com.example.apartment_predictor.repository.TownHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyService {

    @Autowired
    private ApartmentRepository apartmentRepo;
    @Autowired
    private HouseRepository houseRepo;
    @Autowired
    private DuplexRepository duplexRepo;
    @Autowired
    private TownHouseRepository townRepo;

    public Property findById(String id) {
        return apartmentRepo.findById(id)
                .map(p -> (Property) p)
                .or(() -> houseRepo.findById(id).map(p -> (Property) p))
                .or(() -> duplexRepo.findById(id).map(p -> (Property) p))
                .or(() -> townRepo.findById(id).map(p -> (Property) p))
                .orElse(null);
    }

    public Page<Property> findAllPaged(PageRequest pageRequest, String type, Integer minPrice, Integer maxPrice,
            Integer minArea, Integer maxArea, Integer minBedrooms, Integer maxBedrooms) {
        
        if (type != null) {
            return switch (type.toUpperCase()) {
                case "APARTMENT" -> apartmentRepo.findByFilters(minPrice, maxPrice, minArea, maxArea, minBedrooms, maxBedrooms, pageRequest)
                    .map(p -> (Property) p);
                case "HOUSE" -> houseRepo.findByFilters(minPrice, maxPrice, minArea, maxArea, minBedrooms, maxBedrooms, pageRequest)
                    .map(p -> (Property) p);
                case "DUPLEX" -> duplexRepo.findByFilters(minPrice, maxPrice, minArea, maxArea, minBedrooms, maxBedrooms, pageRequest)
                    .map(p -> (Property) p);
                case "TOWNHOUSE" -> townRepo.findByFilters(minPrice, maxPrice, minArea, maxArea, minBedrooms, maxBedrooms, pageRequest)
                    .map(p -> (Property) p);
                default -> Page.empty();
            };
        }
        
        var apartments = apartmentRepo.findByFilters(minPrice, maxPrice, minArea, maxArea, minBedrooms, maxBedrooms, pageRequest)
                .getContent();
        var houses = houseRepo.findByFilters(minPrice, maxPrice, minArea, maxArea, minBedrooms, maxBedrooms, pageRequest)
                .getContent();
        var duplexes = duplexRepo.findByFilters(minPrice, maxPrice, minArea, maxArea, minBedrooms, maxBedrooms, pageRequest)
                .getContent();
        var townHouses = townRepo.findByFilters(minPrice, maxPrice, minArea, maxArea, minBedrooms, maxBedrooms, pageRequest)
                .getContent();
        
        java.util.List<Property> allProperties = new java.util.ArrayList<>();
        allProperties.addAll(apartments);
        allProperties.addAll(houses);
        allProperties.addAll(duplexes);
        allProperties.addAll(townHouses);
        
        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), allProperties.size());
        List<Property> pageContent = start < allProperties.size() ? allProperties.subList(start, end) : java.util.Collections.emptyList();
        
        return new org.springframework.data.domain.PageImpl<>(pageContent, pageRequest, allProperties.size());
    }

    public Property create(Property property) {
        if (property.getId() == null || property.getId().isEmpty()) {
            property.setId(UUID.randomUUID().toString());
        }
        
        if (property instanceof Apartment) {
            return apartmentRepo.save((Apartment) property);
        } else if (property instanceof House) {
            return houseRepo.save((House) property);
        } else if (property instanceof Duplex) {
            return duplexRepo.save((Duplex) property);
        } else if (property instanceof TownHouse) {
            return townRepo.save((TownHouse) property);
        }
        return null;
    }

    public Property update(String id, Property property) {
        Property existing = findById(id);
        if (existing == null) return null;
        
        existing.setAddress(property.getAddress());
        existing.setPrice(property.getPrice());
        existing.setLatitude(property.getLatitude());
        existing.setLongitude(property.getLongitude());
        
        if (existing instanceof Apartment && property instanceof Apartment) {
            Apartment apt = (Apartment) existing;
            Apartment newApt = (Apartment) property;
            apt.setName(newApt.getName());
            apt.setArea(newApt.getArea());
            apt.setBedrooms(newApt.getBedrooms());
            apt.setBathrooms(newApt.getBathrooms());
            apt.setStories(newApt.getStories());
            return apartmentRepo.save(apt);
        } else if (existing instanceof House && property instanceof House) {
            return houseRepo.save((House) existing);
        } else if (existing instanceof Duplex && property instanceof Duplex) {
            return duplexRepo.save((Duplex) existing);
        } else if (existing instanceof TownHouse && property instanceof TownHouse) {
            return townRepo.save((TownHouse) existing);
        }
        return null;
    }

    public void delete(String id) {
        Property property = findById(id);
        if (property instanceof Apartment) {
            apartmentRepo.deleteById(id);
        } else if (property instanceof House) {
            houseRepo.deleteById(id);
        } else if (property instanceof Duplex) {
            duplexRepo.deleteById(id);
        } else if (property instanceof TownHouse) {
            townRepo.deleteById(id);
        }
    }
}
