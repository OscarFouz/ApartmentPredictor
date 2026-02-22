package com.example.apartment_predictor;

import com.example.apartment_predictor.service.PopulateMasterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApartmentPredictorApplication implements CommandLineRunner {

    private final PopulateMasterService populateMasterService;

    @Value("${app.populate-on-start:false}")
    private boolean populateOnStart;

    public ApartmentPredictorApplication(PopulateMasterService populateMasterService) {
        this.populateMasterService = populateMasterService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApartmentPredictorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (populateOnStart) {
            populateMasterService.populate();
        }
    }
}

