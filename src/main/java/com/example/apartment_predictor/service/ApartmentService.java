package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    // ============================
    // CONSTRUCTOR
    // ============================
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    // ============================
    // FIND ALL
    // ============================
    public Iterable<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    // ============================
    // CREATE
    // ============================
    public Apartment createApartment(Apartment apartment){
        return apartmentRepository.save(apartment);
    }

    // ============================
    // UPDATE DIRECTO
    // ============================
    public Apartment updateApartment(Apartment apartment){
        return apartmentRepository.save(apartment);
    }

    // ============================
    // UPDATE POR ID
    // ============================
    public Apartment updateApartmentById(String id, Apartment newApartment) {

        Optional<Apartment> existingOpt = apartmentRepository.findById(id);
        if (existingOpt.isEmpty()) return null;

        Apartment existing = existingOpt.get();

        existing.setPrice(newApartment.getPrice());
        existing.setArea(newApartment.getArea());
        existing.setBedrooms(newApartment.getBedrooms());
        existing.setBathrooms(newApartment.getBathrooms());
        existing.setStories(newApartment.getStories());
        existing.setMainroad(newApartment.getMainroad());
        existing.setGuestroom(newApartment.getGuestroom());
        existing.setBasement(newApartment.getBasement());
        existing.setHotwaterheating(newApartment.getHotwaterheating());
        existing.setAirconditioning(newApartment.getAirconditioning());
        existing.setParking(newApartment.getParking());
        existing.setPrefarea(newApartment.getPrefarea());
        existing.setFurnishingstatus(newApartment.getFurnishingstatus());

        existing.getNearbySchools().clear();
        if (newApartment.getNearbySchools() != null) {
            existing.getNearbySchools().addAll(newApartment.getNearbySchools());
        }

        return apartmentRepository.save(existing);
    }

    // ============================
    // DELETE
    // ============================
    public void deleteApartment(String id){
        apartmentRepository.deleteById(id);
    }

    // ============================
    // FIND BY ID
    // ============================
    public Apartment findApartmentById(String id){
        return apartmentRepository.findById(id).orElse(null);
    }
}
