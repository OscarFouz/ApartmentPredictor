package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.*;
import com.example.apartment_predictor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PopulateDB {

    // ============================
    // REPOSITORIES
    // ============================
    @Autowired private ApartmentRepository apartmentRepository;
    @Autowired private HouseRepository houseRepository;
    @Autowired private DuplexRepository duplexRepository;
    @Autowired private TownHouseRepository townHouseRepository;

    @Autowired private SchoolRepository schoolRepository;
    @Autowired private ReviewerRepository reviewerRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private OwnerRepository ownerRepository;
    @Autowired private PropertyContractRepository propertyContractRepository;

    // ============================
    // ORCHESTRATOR
    // ============================
    public int populateAll(int ownersQty, int propertiesQty, int reviewsQty, int schoolsQty) {

        List<Owner> owners = populateOwners(ownersQty);
        List<Property> properties = populateProperties(propertiesQty, owners);
        List<School> schools = populateSchools(schoolsQty);
        assignSchoolsToProperties(properties, schools);

        List<Reviewer> reviewers = populateReviewers(ownersQty);
        //List<Review> reviews = populateReviews(reviewsQty, reviewers, properties);
        List<Review> reviews = populateReviews(reviewsQty);
        assignReviewsToProperty(reviews,properties);
        assignReviewersToReviews(reviews, reviewers);

        //List<PropertyContract> contracts = populateContracts(propertiesQty, owners, properties);
        List<PropertyContract> contracts = populateContracts(propertiesQty);
        assignOwnerAndPropertyToContract(owners,properties,contracts);
        return properties.size();
    }

    // ============================
    // OWNERS
    // ============================
    private List<Owner> populateOwners(int qty) {

        List<Owner> owners = new ArrayList<>();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        String[] firstNames = {"Carlos", "Lucía", "Miguel", "Ana", "Javier", "Marta", "Pablo", "Laura"};
        String[] lastNames = {"García", "López", "Martínez", "Sánchez", "Fernández", "Gómez", "Díaz", "Ruiz"};
        String[] phones = {"+34 600111222", "+34 600333444", "+34 600555666", "+34 600777888", "+34 600999000"};
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com"};

        for (int i = 0; i < qty; i++) {

            String fullName = firstNames[rnd.nextInt(firstNames.length)] + " " +
                    lastNames[rnd.nextInt(lastNames.length)];

            String email = fullName.toLowerCase().replace(" ", ".") + "@" +
                    domains[rnd.nextInt(domains.length)];

            Owner owner = new Owner(
                    fullName,
                    LocalDate.now().minusYears(rnd.nextInt(25, 70)),
                    phones[rnd.nextInt(phones.length)],
                    email,
                    "password123",
                    true,
                    rnd.nextBoolean(),
                    "ID-" + rnd.nextInt(100000, 999999),
                    LocalDate.now().minusDays(rnd.nextInt(0, 365)),
                    rnd.nextInt(0, 3650)
            );

            ownerRepository.save(owner);
            owners.add(owner);
        }

        return owners;
    }

    // ============================
    // PROPERTIES
    // ============================
    private List<Property> populateProperties(int qty, List<Owner> owners) {

        List<Property> properties = new ArrayList<>();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        int apartmentsQty = (int) (qty * 0.40);
        int housesQty = (int) (qty * 0.20);
        int duplexQty = (int) (qty * 0.20);
        int townQty = qty - apartmentsQty - housesQty - duplexQty;

        for (int i = 0; i < apartmentsQty; i++) {
            properties.add(createApartment(owners));
        }
        for (int i = 0; i < housesQty; i++) {
            properties.add(createHouse(owners));
        }
        for (int i = 0; i < duplexQty; i++) {
            properties.add(createDuplex(owners));
        }
        for (int i = 0; i < townQty; i++) {
            properties.add(createTownHouse(owners));
        }

        return properties;
    }

    private Apartment createApartment(List<Owner> owners) {

        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        Apartment a = new Apartment();
        a.setPrice(rnd.nextInt(30000, 600000));
        a.setArea(rnd.nextInt(300, 5000));
        a.setBedrooms(rnd.nextInt(1, 6));
        a.setBathrooms(rnd.nextInt(1, 4));
        a.setStories(rnd.nextInt(1, 5));
        a.setMainroad(rnd.nextBoolean() ? "yes" : "no");
        a.setGuestroom(rnd.nextBoolean() ? "yes" : "no");
        a.setBasement(rnd.nextBoolean() ? "yes" : "no");
        a.setHotwaterheating(rnd.nextBoolean() ? "yes" : "no");
        a.setAirconditioning(rnd.nextBoolean() ? "yes" : "no");
        a.setParking(rnd.nextInt(0, 3));
        a.setPrefarea(rnd.nextBoolean() ? "yes" : "no");
        a.setFurnishingstatus("furnished");

        a.setName("Apartment " + rnd.nextInt(1000));
        a.setAddress("Street " + rnd.nextInt(1, 200));
        a.setOwner(owners.get(rnd.nextInt(owners.size())));

        apartmentRepository.save(a);
        return a;
    }

    private House createHouse(List<Owner> owners) {

        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        House h = new House();
        h.setName("House " + rnd.nextInt(1000));
        h.setAddress("Avenue " + rnd.nextInt(1, 200));
        h.setOwner(owners.get(rnd.nextInt(owners.size())));

        houseRepository.save(h);
        return h;
    }

    private Duplex createDuplex(List<Owner> owners) {

        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        Duplex d = new Duplex();
        d.setName("Duplex " + rnd.nextInt(1000));
        d.setAddress("Road " + rnd.nextInt(1, 200));
        d.setOwner(owners.get(rnd.nextInt(owners.size())));

        duplexRepository.save(d);
        return d;
    }

    private TownHouse createTownHouse(List<Owner> owners) {

        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        TownHouse t = new TownHouse();
        t.setName("TownHouse " + rnd.nextInt(1000));
        t.setAddress("Lane " + rnd.nextInt(1, 200));
        t.setOwner(owners.get(rnd.nextInt(owners.size())));

        townHouseRepository.save(t);
        return t;
    }

    // ============================
    // SCHOOLS
    // ============================
    private List<School> populateSchools(int qty) {

        List<School> schools = new ArrayList<>();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        String[] types = {"public", "private", "religious"};
        String[] locations = {"Downtown", "Uptown", "Suburbs", "East Side", "West Side"};

        for (int i = 0; i < qty; i++) {

            School s = new School(
                    "School " + rnd.nextInt(1000),
                    "Address " + rnd.nextInt(1, 200),
                    types[rnd.nextInt(types.length)],
                    "Primary",
                    locations[rnd.nextInt(locations.length)],
                    rnd.nextInt(1, 5),
                    true
            );

            schoolRepository.save(s);
            schools.add(s);
        }

        return schools;
    }

    private void assignSchoolsToProperties(List<Property> properties, List<School> schools) {

        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        for (Property p : properties) {

            int qty = rnd.nextInt(1, 4);

            for (int i = 0; i < qty; i++) {
                p.getNearbySchools().add(schools.get(rnd.nextInt(schools.size())));
            }

            if (p instanceof Apartment) apartmentRepository.save((Apartment) p);
            else if (p instanceof House) houseRepository.save((House) p);
            else if (p instanceof Duplex) duplexRepository.save((Duplex) p);
            else if (p instanceof TownHouse) townHouseRepository.save((TownHouse) p);
        }
    }

    // ============================
    // REVIEWERS
    // ============================
    private List<Reviewer> populateReviewers(int qty) {

        List<Reviewer> reviewers = new ArrayList<>();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emily"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones"};
        String[] phones = {"+34 600111222", "+34 600333444"};
        String[] domains = {"gmail.com", "yahoo.com"};

        for (int i = 0; i < qty; i++) {

            String fullName = firstNames[rnd.nextInt(firstNames.length)] + " " +
                    lastNames[rnd.nextInt(lastNames.length)];

            String email = fullName.toLowerCase().replace(" ", ".") + "@" +
                    domains[rnd.nextInt(domains.length)];

            Reviewer r = new Reviewer(
                    fullName,
                    LocalDate.now().minusYears(rnd.nextInt(18, 70)),
                    phones[rnd.nextInt(phones.length)],
                    email,
                    "password123",
                    true,
                    rnd.nextInt(0, 100),
                    rnd.nextBoolean(),
                    "x_" + rnd.nextInt(10000),
                    "https://site.com/" + fullName.replace(" ", "").toLowerCase(),
                    rnd.nextInt(0, 50)
            );

            reviewerRepository.save(r);
            reviewers.add(r);
        }

        return reviewers;
    }

    // ============================
    // REVIEWS
    // ============================
    //    private List<Review> populateReviews(int qty, List<Reviewer> reviewers, List<Property> properties) {
    //
    //        List<Review> reviews = new ArrayList<>();
    //        ThreadLocalRandom rnd = ThreadLocalRandom.current();
    //
    //        String[] titles = {"Great place", "Not bad", "Could be better", "Excellent", "Terrible"};
    //        String[] contents = {"Good experience", "Average", "Bad experience", "Loved it", "Not recommended"};
    //
    //        for (int i = 0; i < qty; i++) {
    //
    //            Review r = new Review(
    //                    titles[rnd.nextInt(titles.length)],
    //                    contents[rnd.nextInt(contents.length)],
    //                    rnd.nextInt(1, 5),
    //                    LocalDate.now().minusDays(rnd.nextInt(0, 365)),
    //                    null,
    //                    null
    //            );
    //
    //            Reviewer reviewer = reviewers.get(rnd.nextInt(reviewers.size()));
    //            Property property = properties.get(rnd.nextInt(properties.size()));
    //
    //            r.setReviewer(reviewer);
    //            r.setProperty(property);
    //
    //            reviewRepository.save(r);
    //            reviews.add(r);
    //        }
    //
    //        return reviews;
    //    }
    private List<Review> populateReviews(int qty) {
        List<Review> reviews = new ArrayList<>();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        String[] titles = {"Great place", "Not bad", "Could be better", "Excellent", "Terrible"};
        String[] contents = {"Good experience", "Average", "Bad experience", "Loved it", "Not recommended"};

        for (int i = 0; i < qty; i++) {
            Review r = new Review(
                    titles[rnd.nextInt(titles.length)],
                    contents[rnd.nextInt(contents.length)],
                    rnd.nextInt(1, 5),
                    LocalDate.now().minusDays(rnd.nextInt(0, 365)),
                    null,
                    null
            );
            reviewRepository.save(r);
            reviews.add(r);
        }
        return reviews;
    }

    private void assignReviewsToProperty(List<Review> reviews, List<Property> properties) {

        Random rnd = new Random();

        for (Property property : properties) {
            Review randomReview = reviews.get(rnd.nextInt(reviews.size()));
            randomReview.setProperty(property);
            property.getReviews().add(randomReview);
        }
    }


    private void assignReviewersToReviews(List<Review> reviews, List<Reviewer> reviewers) {
        Random rnd = new Random();

        for (Review review : reviews) {
            Reviewer randomReviewer = reviewers.get(rnd.nextInt(reviewers.size()));
            review.setReviewer(randomReviewer);
            randomReviewer.getReviews().add(review);
        }
    }


    // ============================
    // CONTRACTS
    // ============================
    //    private List<PropertyContract> populateContracts(int qty, List<Owner> owners, List<Property> properties) {
    //
    //        List<PropertyContract> contracts = new ArrayList<>();
    //        ThreadLocalRandom rnd = ThreadLocalRandom.current();
    //
    //        String[] names = {"Standard Contract", "Premium Contract", "Short Lease", "Long Lease"};
    //        String[] details = {"Basic terms", "Extended terms", "Short duration", "Long duration"};
    //
    //        for (int i = 0; i < qty; i++) {
    //
    //            Owner owner = owners.get(rnd.nextInt(owners.size()));
    //            Property property = properties.get(rnd.nextInt(properties.size()));
    //
    //            PropertyContract c = new PropertyContract(
    //                    names[rnd.nextInt(names.length)],
    //                    details[rnd.nextInt(details.length)],
    //                    rnd.nextDouble(500, 5000),
    //                    LocalDate.now().minusDays(rnd.nextInt(0, 365)),
    //                    owner,
    //                    property
    //            );
    //
    //            c.setEndDate(c.getStartDate().plusDays(rnd.nextInt(30, 365)));
    //
    //            propertyContractRepository.save(c);
    //            contracts.add(c);
    //        }
    //
    //        return contracts;
    //    }
    //}
    private List<PropertyContract> populateContracts(int qty) {

        List<PropertyContract> contracts = new ArrayList<>();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        String[] names = {"Standard Contract", "Premium Contract", "Short Lease", "Long Lease"};
        String[] details = {"Basic terms", "Extended terms", "Short duration", "Long duration"};

        for (int i = 0; i < qty; i++) {

            PropertyContract propertyContract = new PropertyContract(
                    names[rnd.nextInt(names.length)],
                    details[rnd.nextInt(details.length)],
                    rnd.nextDouble(500, 5000),
                    LocalDate.now().minusDays(rnd.nextInt(0, 365)),
                    null,
                    null
            );

            propertyContract.setEndDate(propertyContract.getStartDate().plusDays(rnd.nextInt(30, 365)));

            propertyContractRepository.save(propertyContract);
            contracts.add(propertyContract);
        }

        return contracts;
    }

    private void assignOwnerAndPropertyToContract(List<Owner> owners, List<Property> properties, List<PropertyContract> propertyContracts){
        Random rnd = new Random();

    //        for (PropertyContract propertyContract : propertyContracts) {
    //            Owner randomOwner = owners.get(rnd.nextInt(owners.size()));
    //            Property randomProperty = properties.get(rnd.nextInt(properties.size()));
    //
    //            propertyContract.se.setContracts(randomOwner);
    //            randomProperty.setOwner(randomOwner);
    //            property.getReviews().add(randomReview);
    //        }
    }
}