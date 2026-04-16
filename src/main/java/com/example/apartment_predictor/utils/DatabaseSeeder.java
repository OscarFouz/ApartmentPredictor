package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.*;
import com.example.apartment_predictor.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseSeeder {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private DuplexRepository duplexRepository;
    @Autowired
    private TownHouseRepository townHouseRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private ReviewerRepository reviewerRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PropertyContractRepository propertyContractRepository;

    public void saveOwners(List<Owner> owners) {
        ownerRepository.saveAll(owners);
    }

    public void saveProperties(List<Property> properties) {
        List<Apartment> apartments = new java.util.ArrayList<>();
        List<House> houses = new java.util.ArrayList<>();
        List<Duplex> duplexes = new java.util.ArrayList<>();
        List<TownHouse> townhouses = new java.util.ArrayList<>();

        for (Property p : properties) {
            if (p instanceof Apartment a) apartments.add(a);
            else if (p instanceof House h) houses.add(h);
            else if (p instanceof Duplex d) duplexes.add(d);
            else if (p instanceof TownHouse t) townhouses.add(t);
        }

        apartmentRepository.saveAll(apartments);
        houseRepository.saveAll(houses);
        duplexRepository.saveAll(duplexes);
        townHouseRepository.saveAll(townhouses);
    }

    public void saveSchools(List<School> schools) {
        schoolRepository.saveAll(schools);
    }

    public void saveReviewers(List<Reviewer> reviewers) {
        reviewerRepository.saveAll(reviewers);
    }

    public void saveReviews(List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }

    public void saveContracts(List<PropertyContract> contracts) {
        propertyContractRepository.saveAll(contracts);
    }

    public void updatePropertiesWithRelations(List<Property> properties) {
        List<Apartment> apartments = new java.util.ArrayList<>();
        List<House> houses = new java.util.ArrayList<>();
        List<Duplex> duplexes = new java.util.ArrayList<>();
        List<TownHouse> townhouses = new java.util.ArrayList<>();

        for (Property p : properties) {
            if (p instanceof Apartment a) apartments.add(a);
            else if (p instanceof House h) houses.add(h);
            else if (p instanceof Duplex d) duplexes.add(d);
            else if (p instanceof TownHouse t) townhouses.add(t);
        }

        apartmentRepository.saveAll(apartments);
        houseRepository.saveAll(houses);
        duplexRepository.saveAll(duplexes);
        townHouseRepository.saveAll(townhouses);
    }

    public void clearEntityManager() {
        entityManager.clear();
    }
}
