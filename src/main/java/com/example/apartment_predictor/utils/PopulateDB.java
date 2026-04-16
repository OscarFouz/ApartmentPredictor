package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PopulateDB {

    private final DataGenerator dataGenerator;
    private final DatabaseSeeder databaseSeeder;

    @Autowired
    public PopulateDB(DataGenerator dataGenerator, DatabaseSeeder databaseSeeder) {
        this.dataGenerator = dataGenerator;
        this.databaseSeeder = databaseSeeder;
    }

    public int populateAll(int ownersQty, int propertiesQty, int reviewsQty, int schoolsQtyIgnored) {
        List<Owner> owners = dataGenerator.generateOwners(ownersQty);
        databaseSeeder.saveOwners(owners);

        List<School> schools = dataGenerator.generateSchools();
        databaseSeeder.saveSchools(schools);

        List<Property> properties = dataGenerator.generateProperties(propertiesQty, owners);
        dataGenerator.assignSchoolsToProperties(properties, schools);
        databaseSeeder.saveProperties(properties);

        List<Reviewer> reviewers = dataGenerator.generateReviewers(ownersQty);
        databaseSeeder.saveReviewers(reviewers);

        List<Review> reviews = dataGenerator.generateReviews(reviewsQty);
        databaseSeeder.saveReviews(reviews);
        dataGenerator.assignReviewsToProperty(reviews, properties);
        dataGenerator.assignReviewersToReviews(reviews, reviewers);
        databaseSeeder.updatePropertiesWithRelations(properties);
        databaseSeeder.saveReviews(reviews);

        List<PropertyContract> contracts = dataGenerator.generateContracts(propertiesQty);
        dataGenerator.assignOwnerAndPropertyToContract(owners, properties, contracts);
        databaseSeeder.clearEntityManager();
        databaseSeeder.saveContracts(contracts);

        return properties.size();
    }
}
