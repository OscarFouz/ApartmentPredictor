package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApartmentService {

    @Autowired
    ApartmentRepository apartmentRepository;

    public Iterable<Apartment> findAll() {
        return apartmentRepository.findAll();
    }

    public Apartment createApartment(Apartment apartment){
        if (apartment.getReviews() != null) {
            for (Review r : apartment.getReviews()) {
                r.setApartment(apartment);
            }
        }
        return apartmentRepository.save(apartment);
    }

    public Apartment updateApartment(Apartment apartment){
        return apartmentRepository.save(apartment);
    }

    /**
     * PUT REAL: Reemplaza completamente el apartamento existente por el nuevo.
     */
    public Apartment updateApartmentById(String id, Apartment newApartment) {

        Optional<Apartment> existingOpt = apartmentRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }

        Apartment existing = existingOpt.get();

        // Limpiar reviews antiguas para evitar duplicados o merges raros
        existing.getReviews().clear();

        // Reasignar reviews al nuevo objeto
        if (newApartment.getReviews() != null) {
            for (Review r : newApartment.getReviews()) {
                r.setApartment(newApartment);
            }
        }

        // Mantener el ID original (tu entidad no tiene setId)
        return apartmentRepository.save(newApartment);
    }

    public void deleteApartment(String id){
        apartmentRepository.deleteById(id);
    }

    public Apartment findApartmentById(String id){
        return apartmentRepository.findById(id).orElse(null);
    }
}
