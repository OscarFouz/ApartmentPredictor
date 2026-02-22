package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.*;
import com.example.apartment_predictor.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PopulateMasterService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private DuplexRepository duplexRepository;

    @Autowired
    private TownHouseRepository townHouseRepository;

    @Autowired
    private PropertyContractRepository propertyContractRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private final Faker faker = new Faker(new Locale("es"));
    private final Random random = new Random();

    // ============================
    // MÉTODO PARA ARRANQUE OPCIONAL
    // ============================
    public void populate() {
        populateAll(10, 50, 100, 20);
    }

    // ============================
    // MÉTODO PRINCIPAL
    // ============================
    public int populateAll(int ownersQty, int propertiesQty, int reviewsQty, int schoolsQty) {

        // ============================
        // CONTROL: SI YA HAY DATOS, NO POBLAR
        // ============================
        if (ownerRepository.count() > 0 ||
                apartmentRepository.count() > 0 ||
                houseRepository.count() > 0 ||
                duplexRepository.count() > 0 ||
                townHouseRepository.count() > 0) {

            return -1; // señal de que ya estaba poblada
        }

        // ============================
        // CREATE SCHOOLS
        // ============================
        List<School> schools = new ArrayList<>();
        for (int i = 0; i < schoolsQty; i++) {
            School school = new School(
                    faker.university().name(),
                    faker.address().fullAddress(),
                    faker.options().option("público", "privado", "concertado"),
                    faker.options().option("infantil", "primaria", "secundaria", "bachillerato"),
                    faker.address().city(),
                    faker.number().numberBetween(1, 10),
                    faker.bool().bool()
            );
            schools.add(schoolRepository.save(school));
        }

        // ============================
        // CREATE OWNERS
        // ============================
        List<Owner> owners = new ArrayList<>();
        for (int i = 0; i < ownersQty; i++) {
            Owner owner = new Owner(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone()
            );
            owners.add(ownerRepository.save(owner));
        }

        // ============================
        // CREATE REVIEWERS
        // ============================
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < ownersQty; i++) {
            Reviewer reviewer = new Reviewer(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.number().numberBetween(1, 100)
            );
            reviewers.add(reviewerRepository.save(reviewer));
        }

        // ============================
        // CREATE PROPERTIES
        // ============================
        List<Property> allProperties = new ArrayList<>();

        for (int i = 0; i < propertiesQty; i++) {

            Owner owner = owners.get(random.nextInt(owners.size()));
            int type = random.nextInt(4);

            Property property;

            switch (type) {

                // ============================
                // APARTMENT
                // ============================
                case 0 -> {
                    Apartment apt = new Apartment(
                            faker.number().numberBetween(50000, 300000),
                            faker.number().numberBetween(40, 200),
                            faker.number().numberBetween(1, 5),
                            faker.number().numberBetween(1, 3),
                            faker.number().numberBetween(1, 3),
                            faker.bool().bool() ? "yes" : "no",
                            faker.bool().bool() ? "yes" : "no",
                            faker.bool().bool() ? "yes" : "no",
                            faker.bool().bool() ? "yes" : "no",
                            faker.bool().bool() ? "yes" : "no",
                            faker.number().numberBetween(0, 3),
                            faker.bool().bool() ? "yes" : "no",
                            faker.options().option("furnished", "semi-furnished", "unfurnished")
                    );
                    apt.setAddress(faker.address().fullAddress());
                    apt.setOwner(owner);
                    apt.setNearbySchools(getRandomSchools(schools));
                    property = apartmentRepository.save(apt);
                }

                // ============================
                // HOUSE
                // ============================
                case 1 -> {
                    House house = new House(
                            "Casa " + faker.address().streetName(),
                            faker.address().fullAddress(),
                            owner
                    );
                    house.setNearbySchools(getRandomSchools(schools));
                    property = houseRepository.save(house);
                }

                // ============================
                // DUPLEX (usa constructor REAL)
                // ============================
                case 2 -> {
                    Duplex duplex = new Duplex(
                            UUID.randomUUID().toString(),
                            "Dúplex " + faker.address().streetName(),
                            faker.address().fullAddress(),
                            owner,
                            new ArrayList<>()
                    );
                    duplex.setNearbySchools(getRandomSchools(schools));
                    property = duplexRepository.save(duplex);
                }

                // ============================
                // TOWNHOUSE (usa constructor REAL)
                // ============================
                default -> {
                    TownHouse townHouse = new TownHouse(
                            UUID.randomUUID().toString(),
                            "Adosado " + faker.address().streetName(),
                            faker.address().fullAddress(),
                            owner,
                            new ArrayList<>()
                    );
                    townHouse.setNearbySchools(getRandomSchools(schools));
                    property = townHouseRepository.save(townHouse);
                }
            }

            allProperties.add(property);

            // ============================
            // CREATE CONTRACT
            // ============================
            PropertyContract contract = new PropertyContract();
            contract.setOwner(owner);
            contract.setProperty(property);
            contract.setAgreedPrice(faker.number().numberBetween(50000, 300000));
            contract.setStartDate(LocalDate.now().minusDays(faker.number().numberBetween(0, 365)));
            contract.setActive(true);
            propertyContractRepository.save(contract);
        }

        // ============================
        // CREATE REVIEWS
        // ============================
        for (int i = 0; i < reviewsQty; i++) {

            Property property = allProperties.get(random.nextInt(allProperties.size()));
            Reviewer reviewer = reviewers.get(random.nextInt(reviewers.size()));

            Review review = new Review(
                    faker.lorem().sentence(3),
                    faker.lorem().paragraph(),
                    faker.number().numberBetween(1, 5),
                    LocalDate.now().minusDays(faker.number().numberBetween(0, 365)),
                    property,
                    reviewer
            );

            reviewRepository.save(review);
        }

        return ownersQty + propertiesQty + reviewsQty + schoolsQty;
    }

    // ============================
    // MÉTODO AUXILIAR
    // ============================
    private List<School> getRandomSchools(List<School> schools) {
        Collections.shuffle(schools);
        return schools.subList(0, Math.min(3, schools.size()));
    }
}
