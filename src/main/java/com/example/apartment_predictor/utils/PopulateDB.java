package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.*;
import com.example.apartment_predictor.repository.*;
import net.datafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PopulateDB {

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

    private final Faker faker = new Faker();

    // ============================
    // NODOS DEL GRAFO MANHATTAN
    // ============================
    private static class NodeCoord {
        int id;
        double lat;
        double lon;

        NodeCoord(int id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }
    }

    private static final NodeCoord[] GRAPH_NODES = new NodeCoord[]{
            new NodeCoord(1, 40.753182, -73.981533),
            new NodeCoord(2, 40.754215, -73.980498),
            new NodeCoord(3, 40.755249, -73.979463),
            new NodeCoord(4, 40.756283, -73.978428),
            new NodeCoord(5, 40.752550, -73.979130),
            new NodeCoord(6, 40.753580, -73.978100),
            new NodeCoord(7, 40.754610, -73.977070),
            new NodeCoord(8, 40.755640, -73.976040)
    };

    private int nearestNodeId(double lat, double lon) {
        double best = Double.POSITIVE_INFINITY;
        int bestId = GRAPH_NODES[0].id;

        for (NodeCoord n : GRAPH_NODES) {
            double d = haversine(lat, lon, n.lat, n.lon);
            if (d < best) {
                best = d;
                bestId = n.id;
            }
        }
        return bestId;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371e3;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    // ============================
    // ORQUESTADOR PRINCIPAL
    // ============================
    public int populateAll(int ownersQty, int propertiesQty, int reviewsQty, int schoolsQtyIgnored) {

        List<Owner> owners = populateOwners(ownersQty);
        List<Property> properties = populateProperties(propertiesQty, owners);
        List<School> schools = populateSchoolsManhattan();
        assignSchoolsToProperties(properties, schools);

        List<Reviewer> reviewers = populateReviewers(ownersQty);

        List<Review> reviews = populateReviews(reviewsQty);
        assignReviewsToProperty(reviews, properties);
        assignReviewersToReviews(reviews, reviewers);

        List<PropertyContract> contracts = populateContracts(propertiesQty);

        entityManager.clear();

        assignOwnerAndPropertyToContract(owners, properties, contracts);

        return properties.size();
    }

    // ============================
    // OWNERS (FAKER)
    // ============================
    private List<Owner> populateOwners(int qty) {

        List<Owner> owners = new ArrayList<>();

        for (int i = 0; i < qty; i++) {

            Owner owner = new Owner(
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
            );

            ownerRepository.save(owner);
            owners.add(owner);
        }

        return owners;
    }

    // ============================
    // DIRECCIONES REALES DE MANHATTAN
    // ============================
    private static class AddressCoord {
        String address;
        double lat;
        double lon;

        AddressCoord(String address, double lat, double lon) {
            this.address = address;
            this.lat = lat;
            this.lon = lon;
        }
    }

    private static final AddressCoord[] MANHATTAN_ADDRESSES = new AddressCoord[]{
            new AddressCoord("350 5th Ave, New York, NY 10118", 40.748440, -73.985664),
            new AddressCoord("11 Madison Ave, New York, NY 10010", 40.741040, -73.987600),
            new AddressCoord("110 W 42nd St, New York, NY 10036", 40.755230, -73.984600),
            new AddressCoord("15 E 44th St, New York, NY 10017", 40.753700, -73.979600),
            new AddressCoord("109 E 42nd St, New York, NY 10017", 40.751900, -73.975400),
            new AddressCoord("200 Madison Ave, New York, NY 10016", 40.749800, -73.983000),
            new AddressCoord("405 Lexington Ave, New York, NY 10174", 40.751620, -73.975500),
            new AddressCoord("30 Rockefeller Plaza, New York, NY 10112", 40.758740, -73.978674)
    };

    private AddressCoord randomManhattanAddress() {
        return MANHATTAN_ADDRESSES[ThreadLocalRandom.current().nextInt(MANHATTAN_ADDRESSES.length)];
    }

    // ============================
    // PROPERTIES (FAKER + COORDENADAS REALES)
    // ============================
    private List<Property> populateProperties(int qty, List<Owner> owners) {

        List<Property> properties = new ArrayList<>();

        int apartmentsQty = (int) (qty * 0.40);
        int housesQty = (int) (qty * 0.20);
        int duplexQty = (int) (qty * 0.20);
        int townQty = qty - apartmentsQty - housesQty - duplexQty;

        for (int i = 0; i < apartmentsQty; i++) properties.add(createApartment(owners));
        for (int i = 0; i < housesQty; i++) properties.add(createHouse(owners));
        for (int i = 0; i < duplexQty; i++) properties.add(createDuplex(owners));
        for (int i = 0; i < townQty; i++) properties.add(createTownHouse(owners));

        return properties;
    }

    private Apartment createApartment(List<Owner> owners) {

        AddressCoord ac = randomManhattanAddress();

        Apartment a = new Apartment();
        a.setName("Apartment " + faker.number().numberBetween(1, 999));
        a.setAddress(ac.address);
        a.setLatitude(ac.lat);
        a.setLongitude(ac.lon);
        a.setNearestNodeId(nearestNodeId(ac.lat, ac.lon));

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

        apartmentRepository.save(a);
        return a;
    }

    private House createHouse(List<Owner> owners) {

        AddressCoord ac = randomManhattanAddress();

        House h = new House();
        h.setName("House " + faker.number().numberBetween(1, 999));
        h.setAddress(ac.address);
        h.setLatitude(ac.lat);
        h.setLongitude(ac.lon);
        h.setNearestNodeId(nearestNodeId(ac.lat, ac.lon));

        h.setPrice(faker.number().numberBetween(50000, 700000));
        h.setOwner(owners.get(faker.number().numberBetween(0, owners.size())));

        houseRepository.save(h);
        return h;
    }

    private Duplex createDuplex(List<Owner> owners) {

        AddressCoord ac = randomManhattanAddress();

        Duplex d = new Duplex();
        d.setName("Duplex " + faker.number().numberBetween(1, 999));
        d.setAddress(ac.address);
        d.setLatitude(ac.lat);
        d.setLongitude(ac.lon);
        d.setNearestNodeId(nearestNodeId(ac.lat, ac.lon));

        d.setPrice(faker.number().numberBetween(60000, 800000));
        d.setOwner(owners.get(faker.number().numberBetween(0, owners.size())));

        duplexRepository.save(d);
        return d;
    }

    private TownHouse createTownHouse(List<Owner> owners) {

        AddressCoord ac = randomManhattanAddress();

        TownHouse t = new TownHouse();
        t.setName("TownHouse " + faker.number().numberBetween(1, 999));
        t.setAddress(ac.address);
        t.setLatitude(ac.lat);
        t.setLongitude(ac.lon);
        t.setNearestNodeId(nearestNodeId(ac.lat, ac.lon));

        t.setPrice(faker.number().numberBetween(70000, 900000));
        t.setOwner(owners.get(faker.number().numberBetween(0, owners.size())));

        townHouseRepository.save(t);
        return t;
    }

    // ============================
    // SCHOOLS REALES DE MANHATTAN
    // ============================
    private List<School> populateSchoolsManhattan() {

        List<School> schools = new ArrayList<>();

        schools.add(new School(
                "PS 59 Beekman Hill International",
                "233 E 56th St, New York, NY 10022",
                "public",
                "Primary",
                "Midtown East",
                4,
                true,
                40.760800,
                -73.967700,
                nearestNodeId(40.760800, -73.967700)
        ));

        schools.add(new School(
                "PS 51 Elias Howe",
                "520 W 45th St, New York, NY 10036",
                "public",
                "Primary",
                "Hell's Kitchen",
                4,
                true,
                40.760200,
                -73.994000,
                nearestNodeId(40.760200, -73.994000)
        ));

        schools.add(new School(
                "The Browning School",
                "52 E 62nd St, New York, NY 10065",
                "private",
                "Secondary",
                "Upper East Side",
                5,
                false,
                40.764900,
                -73.970900,
                nearestNodeId(40.764900, -73.970900)
        ));

        schools.add(new School(
                "Lycee Francais de New York",
                "505 E 75th St, New York, NY 10021",
                "private",
                "Secondary",
                "Upper East Side",
                5,
                false,
                40.770400,
                -73.953600,
                nearestNodeId(40.770400, -73.953600)
        ));

        schools.add(new School(
                "PS 6 Lillie Devereaux Blake",
                "45 E 81st St, New York, NY 10028",
                "public",
                "Primary",
                "Upper East Side",
                5,
                true,
                40.776200,
                -73.958000,
                nearestNodeId(40.776200, -73.958000)
        ));

        schools.forEach(schoolRepository::save);

        return schools;
    }

    // ============================
    // ASIGNAR SCHOOLS A PROPERTIES
    // ============================
    private void assignSchoolsToProperties(List<Property> properties, List<School> schools) {

        for (Property p : properties) {
            int qty = faker.number().numberBetween(1, 4);
            for (int i = 0; i < qty; i++) {
                p.getNearbySchools().add(schools.get(faker.number().numberBetween(0, schools.size())));
            }

            if (p instanceof Apartment) apartmentRepository.save((Apartment) p);
            else if (p instanceof House) houseRepository.save((House) p);
            else if (p instanceof Duplex) duplexRepository.save((Duplex) p);
            else if (p instanceof TownHouse) townHouseRepository.save((TownHouse) p);
        }
    }

    // ============================
    // REVIEWERS (FAKER)
    // ============================
    private List<Reviewer> populateReviewers(int qty) {

        List<Reviewer> reviewers = new ArrayList<>();

        for (int i = 0; i < qty; i++) {

            Reviewer r = new Reviewer(
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
            );

            reviewerRepository.save(r);
            reviewers.add(r);
        }

        return reviewers;
    }

    // ============================
    // REVIEWS (FAKER)
    // ============================
    private List<Review> populateReviews(int qty) {

        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < qty; i++) {

            Review r = new Review(
                    faker.book().title(),
                    faker.lorem().sentence(),
                    faker.number().numberBetween(1, 5),
                    LocalDate.now().minusDays(faker.number().numberBetween(0, 365)),
                    null,
                    null
            );

            reviewRepository.save(r);
            reviews.add(r);
        }

        return reviews;
    }

    private void assignReviewsToProperty(List<Review> reviews, List<Property> properties) {

        for (Property property : properties) {
            Review randomReview = reviews.get(faker.number().numberBetween(0, reviews.size()));
            randomReview.setProperty(property);
            property.getReviews().add(randomReview);

            reviewRepository.save(randomReview);

            if (property instanceof Apartment) apartmentRepository.save((Apartment) property);
            else if (property instanceof House) houseRepository.save((House) property);
            else if (property instanceof Duplex) duplexRepository.save((Duplex) property);
            else if (property instanceof TownHouse) townHouseRepository.save((TownHouse) property);
        }
    }

    private void assignReviewersToReviews(List<Review> reviews, List<Reviewer> reviewers) {

        for (Review review : reviews) {
            Reviewer randomReviewer = reviewers.get(faker.number().numberBetween(0, reviewers.size()));
            review.setReviewer(randomReviewer);
            randomReviewer.getReviews().add(review);

            reviewRepository.save(review);
        }
    }

    // ============================
    // CONTRACTS (FAKER)
    // ============================
    private List<PropertyContract> populateContracts(int qty) {

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

    private void assignOwnerAndPropertyToContract(List<Owner> owners, List<Property> properties, List<PropertyContract> propertyContracts) {

        for (PropertyContract contract : propertyContracts) {

            Owner randomOwner = owners.get(faker.number().numberBetween(0, owners.size()));
            Property randomProperty = properties.get(faker.number().numberBetween(0, properties.size()));

            contract.setOwner(randomOwner);
            contract.setProperty(randomProperty);

            randomOwner.getContracts().add(contract);
            randomProperty.getPropertyContracts().add(contract);

            propertyContractRepository.save(contract);
        }
    }
}