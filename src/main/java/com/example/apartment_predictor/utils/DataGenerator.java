package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.*;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataGenerator {

    private final Faker faker = new Faker();
    private final GraphInitializer graphInitializer;

    private record AddressCoord(String address, double lat, double lon) {}

    private static final AddressCoord[] MANHATTAN_ADDRESSES = {
            new AddressCoord("350 5th Ave, New York, NY 10118", 40.748440, -73.985664),
            new AddressCoord("11 Madison Ave, New York, NY 10010", 40.741040, -73.987600),
            new AddressCoord("110 W 42nd St, New York, NY 10036", 40.755230, -73.984600),
            new AddressCoord("15 E 44th St, New York, NY 10017", 40.753700, -73.979600),
            new AddressCoord("109 E 42nd St, New York, NY 10017", 40.751900, -73.975400),
            new AddressCoord("200 Madison Ave, New York, NY 10016", 40.749800, -73.983000),
            new AddressCoord("405 Lexington Ave, New York, NY 10174", 40.751620, -73.975500),
            new AddressCoord("30 Rockefeller Plaza, New York, NY 10112", 40.758740, -73.978674)
    };

    public DataGenerator(GraphInitializer graphInitializer) {
        this.graphInitializer = graphInitializer;
    }

    public List<Owner> generateOwners(int qty) {
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            owners.add(new Owner(
                    faker.name().fullName(),
                    faker.date().birthday(25, 70).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                    faker.phoneNumber().cellPhone(),
                    faker.internet().emailAddress(),
                    "password123",
                    true,
                    faker.bool().bool(),
                    "ID-" + faker.number().numberBetween(100000, 999999),
                    LocalDate.now().minusDays(faker.number().numberBetween(0, 365)),
                    faker.number().numberBetween(0, 3650)
            ));
        }
        return owners;
    }

    public List<Reviewer> generateReviewers(int qty) {
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            reviewers.add(new Reviewer(
                    faker.name().fullName(),
                    faker.date().birthday(18, 70).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                    faker.phoneNumber().cellPhone(),
                    faker.internet().emailAddress(),
                    "password123",
                    true,
                    faker.number().numberBetween(0, 100),
                    faker.bool().bool(),
                    "x_" + faker.number().numberBetween(1000, 9999),
                    faker.internet().url(),
                    faker.number().numberBetween(0, 50)
            ));
        }
        return reviewers;
    }

    public List<Review> generateReviews(int qty) {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            reviews.add(new Review(
                    faker.book().title(),
                    faker.lorem().sentence(),
                    faker.number().numberBetween(1, 5),
                    LocalDate.now().minusDays(faker.number().numberBetween(0, 365)),
                    null,
                    null
            ));
        }
        return reviews;
    }

    public List<PropertyContract> generateContracts(int qty) {
        List<PropertyContract> contracts = new ArrayList<>();
        for (int i = 0; i < qty; i++) {
            PropertyContract c = new PropertyContract(
                    faker.commerce().productName(),
                    faker.lorem().sentence(),
                    faker.number().randomDouble(2, 500, 5000),
                    LocalDate.now().minusDays(faker.number().numberBetween(0, 365)),
                    null,
                    null
            );
            c.setEndDate(c.getStartDate().plusDays(faker.number().numberBetween(30, 365)));
            contracts.add(c);
        }
        return contracts;
    }

    public List<School> generateSchools() {
        List<School> schools = new ArrayList<>();
        schools.add(new School("PS 59 Beekman Hill International", "233 E 56th St, New York, NY 10022", "public", "Primary", "Midtown East", 4, true, 40.760800, -73.967700, graphInitializer.nearestNodeId(40.760800, -73.967700)));
        schools.add(new School("PS 51 Elias Howe", "520 W 45th St, New York, NY 10036", "public", "Primary", "Hell's Kitchen", 4, true, 40.760200, -73.994000, graphInitializer.nearestNodeId(40.760200, -73.994000)));
        schools.add(new School("The Browning School", "52 E 62nd St, New York, NY 10065", "private", "Secondary", "Upper East Side", 5, false, 40.764900, -73.970900, graphInitializer.nearestNodeId(40.764900, -73.970900)));
        schools.add(new School("Lycee Francais de New York", "505 E 75th St, New York, NY 10021", "private", "Secondary", "Upper East Side", 5, false, 40.770400, -73.953600, graphInitializer.nearestNodeId(40.770400, -73.953600)));
        schools.add(new School("PS 6 Lillie Devereaux Blake", "45 E 81st St, New York, NY 10028", "public", "Primary", "Upper East Side", 5, true, 40.776200, -73.958000, graphInitializer.nearestNodeId(40.776200, -73.958000)));
        return schools;
    }

    public List<Property> generateProperties(int qty, List<Owner> owners) {
        List<Property> properties = new ArrayList<>();
        List<Apartment> apartments = new ArrayList<>();
        List<House> houses = new ArrayList<>();
        List<Duplex> duplexes = new ArrayList<>();
        List<TownHouse> townhouses = new ArrayList<>();

        int apartmentsQty = (int) (qty * 0.40);
        int housesQty = (int) (qty * 0.20);
        int duplexQty = (int) (qty * 0.20);
        int townQty = qty - apartmentsQty - housesQty - duplexQty;

        for (int i = 0; i < apartmentsQty; i++) apartments.add(createApartment(owners));
        for (int i = 0; i < housesQty; i++) houses.add(createHouse(owners));
        for (int i = 0; i < duplexQty; i++) duplexes.add(createDuplex(owners));
        for (int i = 0; i < townQty; i++) townhouses.add(createTownHouse(owners));

        properties.addAll(apartments);
        properties.addAll(houses);
        properties.addAll(duplexes);
        properties.addAll(townhouses);

        return properties;
    }

    private AddressCoord randomAddress() {
        return MANHATTAN_ADDRESSES[ThreadLocalRandom.current().nextInt(MANHATTAN_ADDRESSES.length)];
    }

    private Apartment createApartment(List<Owner> owners) {
        AddressCoord ac = randomAddress();
        Apartment a = new Apartment();
        a.setName("Apartment " + faker.number().numberBetween(1, 999));
        a.setAddress(ac.address);
        a.setLatitude(ac.lat);
        a.setLongitude(ac.lon);
        a.setNearestNodeId(graphInitializer.nearestNodeId(ac.lat, ac.lon));
        a.setPrice(faker.number().numberBetween(30000, 600000));
        a.setArea(faker.number().numberBetween(300, 5000));
        a.setBedrooms(faker.number().numberBetween(1, 6));
        a.setBathrooms(faker.number().numberBetween(1, 4));
        a.setStories(faker.number().numberBetween(1, 5));
        a.setMainroad(faker.bool().bool() ? "yes" : "no");
        a.setGuestroom(faker.bool().bool() ? "yes" : "no");
        a.setBasement(faker.bool().bool() ? "yes" : "no");
        a.setHotwaterheating(faker.bool().bool() ? "yes" : "no");
        a.setAirconditioning(faker.bool().bool() ? "yes" : "no");
        a.setParking(faker.number().numberBetween(0, 3));
        a.setPrefarea(faker.bool().bool() ? "yes" : "no");
        a.setFurnishingstatus("furnished");
        a.setOwner(owners.get(faker.number().numberBetween(0, owners.size())));
        return a;
    }

    private House createHouse(List<Owner> owners) {
        AddressCoord ac = randomAddress();
        House h = new House();
        h.setName("House " + faker.number().numberBetween(1, 999));
        h.setAddress(ac.address);
        h.setLatitude(ac.lat);
        h.setLongitude(ac.lon);
        h.setNearestNodeId(graphInitializer.nearestNodeId(ac.lat, ac.lon));
        h.setPrice(faker.number().numberBetween(50000, 700000));
        h.setOwner(owners.get(faker.number().numberBetween(0, owners.size())));
        return h;
    }

    private Duplex createDuplex(List<Owner> owners) {
        AddressCoord ac = randomAddress();
        Duplex d = new Duplex();
        d.setName("Duplex " + faker.number().numberBetween(1, 999));
        d.setAddress(ac.address);
        d.setLatitude(ac.lat);
        d.setLongitude(ac.lon);
        d.setNearestNodeId(graphInitializer.nearestNodeId(ac.lat, ac.lon));
        d.setPrice(faker.number().numberBetween(60000, 800000));
        d.setOwner(owners.get(faker.number().numberBetween(0, owners.size())));
        return d;
    }

    private TownHouse createTownHouse(List<Owner> owners) {
        AddressCoord ac = randomAddress();
        TownHouse t = new TownHouse();
        t.setName("TownHouse " + faker.number().numberBetween(1, 999));
        t.setAddress(ac.address);
        t.setLatitude(ac.lat);
        t.setLongitude(ac.lon);
        t.setNearestNodeId(graphInitializer.nearestNodeId(ac.lat, ac.lon));
        t.setPrice(faker.number().numberBetween(70000, 900000));
        t.setOwner(owners.get(faker.number().numberBetween(0, owners.size())));
        return t;
    }

    public void assignSchoolsToProperties(List<Property> properties, List<School> schools) {
        for (Property p : properties) {
            int qty = faker.number().numberBetween(1, 4);
            for (int i = 0; i < qty; i++) {
                p.getNearbySchools().add(schools.get(faker.number().numberBetween(0, schools.size())));
            }
        }
    }

    public void assignReviewsToProperty(List<Review> reviews, List<Property> properties) {
        for (Property property : properties) {
            Review randomReview = reviews.get(faker.number().numberBetween(0, reviews.size()));
            randomReview.setProperty(property);
            property.getReviews().add(randomReview);
        }
    }

    public void assignReviewersToReviews(List<Review> reviews, List<Reviewer> reviewers) {
        for (Review review : reviews) {
            Reviewer randomReviewer = reviewers.get(faker.number().numberBetween(0, reviewers.size()));
            review.setReviewer(randomReviewer);
            randomReviewer.getReviews().add(review);
        }
    }

    public void assignOwnerAndPropertyToContract(List<Owner> owners, List<Property> properties, List<PropertyContract> contracts) {
        for (PropertyContract contract : contracts) {
            Owner randomOwner = owners.get(faker.number().numberBetween(0, owners.size()));
            Property randomProperty = properties.get(faker.number().numberBetween(0, properties.size()));
            contract.setOwner(randomOwner);
            contract.setProperty(randomProperty);
            randomOwner.getContracts().add(contract);
            randomProperty.getPropertyContracts().add(contract);
        }
    }
}
