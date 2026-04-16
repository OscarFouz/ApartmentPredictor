package com.example.apartment_predictor;

import com.example.apartment_predictor.repository.*;
import com.example.apartment_predictor.utils.PopulateDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class ApartmentPredictorApplication {

    private static final Logger log = LoggerFactory.getLogger(ApartmentPredictorApplication.class);

    @Value("${app.populate-on-start:false}")
    private boolean populateOnStart;

    @Autowired private OwnerRepository ownerRepository;
    @Autowired private ApartmentRepository apartmentRepository;
    @Autowired private HouseRepository houseRepository;
    @Autowired private DuplexRepository duplexRepository;
    @Autowired private TownHouseRepository townHouseRepository;
    @Autowired private SchoolRepository schoolRepository;
    @Autowired private ReviewerRepository reviewerRepository;
    @Autowired private ReviewRepository reviewRepository;

    @Autowired
    private PopulateDB populateDB;

    public static void main(String[] args) {
        SpringApplication.run(ApartmentPredictorApplication.class, args);
    }

    @PostConstruct
    public void init() {

        if (!populateOnStart) {
            log.info("Populate on start disabled.");
            return;
        }

        log.info("Checking if database is empty...");

        boolean dbIsEmpty =
                ownerRepository.count() == 0 &&
                        apartmentRepository.count() == 0 &&
                        houseRepository.count() == 0 &&
                        duplexRepository.count() == 0 &&
                        townHouseRepository.count() == 0 &&
                        schoolRepository.count() == 0 &&
                        reviewerRepository.count() == 0 &&
                        reviewRepository.count() == 0;

        if (!dbIsEmpty) {
            log.info("Database already contains data. Populate skipped.");
            return;
        }

        log.info("Database is empty. Running populate...");

        int owners = 10;
        int properties = 50;
        int reviews = 100;
        int schools = 20;

        int result = populateDB.populateAll(owners, properties, reviews, schools);

        if (result > 0) {
            log.info("Populate completed. Properties created: {}", result);
        } else {
            log.error("Populate failed.");
        }
    }
}
