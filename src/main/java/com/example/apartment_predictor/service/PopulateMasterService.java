package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.*;
import com.example.apartment_predictor.repository.*;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class PopulateMasterService {

    private final ApartmentRepository apartmentRepository;
    private final OwnerRepository ownerRepository;
    private final ReviewerRepository reviewerRepository;
    private final HouseRepository houseRepository;
    private final DuplexRepository duplexRepository;
    private final TownHouseRepository townHouseRepository;
    private final ReviewRepository reviewRepository;
    private final PropertyContractRepository propertyContractRepository;
    private final SchoolRepository schoolRepository;

    private final Random random = new Random();

    public PopulateMasterService(ApartmentRepository apartmentRepository,
                                 OwnerRepository ownerRepository,
                                 ReviewerRepository reviewerRepository,
                                 HouseRepository houseRepository,
                                 DuplexRepository duplexRepository,
                                 TownHouseRepository townHouseRepository,
                                 ReviewRepository reviewRepository,
                                 PropertyContractRepository propertyContractRepository,
                                 SchoolRepository schoolRepository) {

        this.apartmentRepository = apartmentRepository;
        this.ownerRepository = ownerRepository;
        this.reviewerRepository = reviewerRepository;
        this.houseRepository = houseRepository;
        this.duplexRepository = duplexRepository;
        this.townHouseRepository = townHouseRepository;
        this.reviewRepository = reviewRepository;
        this.propertyContractRepository = propertyContractRepository;
        this.schoolRepository = schoolRepository;
    }

    public void populate() {

        Faker faker = new Faker(new Locale("es"));

        // ============================
        // 1. Crear Schools
        // ============================

        for (int i = 0; i < 15; i++) {

            School school = new School(
                    faker.university().name(),
                    faker.address().fullAddress(),
                    faker.options().option("público", "privado", "concertado"),
                    faker.options().option("infantil", "primaria", "secundaria", "bachillerato"),
                    Double.parseDouble(faker.address().latitude().replace(",", ".")),
                    Double.parseDouble(faker.address().longitude().replace(",", ".")),
                    faker.number().randomDouble(1, 3, 10),
                    faker.number().numberBetween(200, 1500)
            );

            schoolRepository.save(school);
        }

        List<School> allSchools = schoolRepository.findAll();

        // ============================
        // 2. Crear Owners
        // ============================

        for (int i = 0; i < 20; i++) {

            Owner owner = new Owner(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone()
            );
            ownerRepository.save(owner);

            // ============================
            // 3. Crear Apartments + Houses + Duplex + TownHouse
            // ============================

            for (int j = 0; j < 3; j++) {

                // ----------------------------
                // APARTMENT
                // ----------------------------
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
                apartmentRepository.save(apt);

                apt.getNearbySchools().addAll(getRandomSchools(allSchools));
                apartmentRepository.save(apt);

                // CONTRATO APARTMENT
                PropertyContract contractApt = new PropertyContract();
                contractApt.setOwner(owner);
                contractApt.setApartment(apt);
                contractApt.setAgreedPrice(apt.getPrice());
                contractApt.setStartDate(LocalDate.now().minusDays(faker.number().numberBetween(0, 365)));
                contractApt.setActive(true);
                propertyContractRepository.save(contractApt);

                // ----------------------------
                // HOUSE
                // ----------------------------
                House house = new House(
                        "Casa " + faker.address().streetName(),
                        faker.address().fullAddress(),
                        apt,
                        owner
                );
                house.getNearbySchools().addAll(getRandomSchools(allSchools));
                houseRepository.save(house);

                // CONTRATO HOUSE
                PropertyContract contractHouse = new PropertyContract();
                contractHouse.setOwner(owner);
                contractHouse.setHouse(house);
                contractHouse.setAgreedPrice(apt.getPrice() + faker.number().numberBetween(5000, 20000));
                contractHouse.setStartDate(LocalDate.now().minusDays(faker.number().numberBetween(0, 365)));
                contractHouse.setActive(true);
                propertyContractRepository.save(contractHouse);

                // ----------------------------
                // DUPLEX
                // ----------------------------
                Duplex duplex = new Duplex(
                        "Dúplex " + faker.address().streetName(),
                        faker.address().fullAddress(),
                        apt,
                        owner
                );
                duplex.getNearbySchools().addAll(getRandomSchools(allSchools));
                duplexRepository.save(duplex);

                // CONTRATO DUPLEX
                PropertyContract contractDuplex = new PropertyContract();
                contractDuplex.setOwner(owner);
                contractDuplex.setDuplex(duplex);
                contractDuplex.setAgreedPrice(apt.getPrice() + faker.number().numberBetween(10000, 30000));
                contractDuplex.setStartDate(LocalDate.now().minusDays(faker.number().numberBetween(0, 365)));
                contractDuplex.setActive(true);
                propertyContractRepository.save(contractDuplex);

                // ----------------------------
                // TOWNHOUSE
                // ----------------------------
                TownHouse townHouse = new TownHouse(
                        "Adosado " + faker.address().streetName(),
                        faker.address().fullAddress(),
                        apt,
                        owner
                );
                townHouse.getNearbySchools().addAll(getRandomSchools(allSchools));
                townHouseRepository.save(townHouse);

                // CONTRATO TOWNHOUSE
                PropertyContract contractTown = new PropertyContract();
                contractTown.setOwner(owner);
                contractTown.setTownHouse(townHouse);
                contractTown.setAgreedPrice(apt.getPrice() + faker.number().numberBetween(8000, 25000));
                contractTown.setStartDate(LocalDate.now().minusDays(faker.number().numberBetween(0, 365)));
                contractTown.setActive(true);
                propertyContractRepository.save(contractTown);
            }
        }

        // ============================
        // 4. Crear Reviewers
        // ============================

        for (int i = 0; i < 15; i++) {
            Reviewer reviewer = new Reviewer(
                    faker.name().fullName(),
                    faker.internet().emailAddress(),
                    faker.number().numberBetween(1, 100)
            );
            reviewerRepository.save(reviewer);
        }

        // ============================
        // 5. Crear Reviews para Apartments
        // ============================

        var apartments = apartmentRepository.findAll();
        var reviewers = reviewerRepository.findAll().iterator();

        for (Apartment apt : apartments) {

            for (int i = 0; i < 3; i++) {

                if (!reviewers.hasNext()) {
                    reviewers = reviewerRepository.findAll().iterator();
                }

                Reviewer reviewer = reviewers.next();

                Review review = new Review(
                        faker.lorem().sentence(3),
                        faker.lorem().paragraph(),
                        faker.number().numberBetween(1, 5),
                        LocalDate.now().minusDays(faker.number().numberBetween(0, 365)),
                        apt,
                        reviewer
                );

                reviewRepository.save(review);
            }
        }

        System.out.println("=== Base de datos poblada correctamente con Schools, Properties y Contracts ===");
    }

    // ============================
    // MÉTODO AUXILIAR
    // ============================

    private List<School> getRandomSchools(List<School> schools) {
        return schools.stream()
                .filter(s -> random.nextBoolean())
                .limit(3)
                .toList();
    }
}
