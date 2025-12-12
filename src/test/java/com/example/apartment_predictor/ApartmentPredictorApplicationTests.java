package com.example.apartment_predictor;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ApartmentRepository;
import com.example.apartment_predictor.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class ApartmentPredictorApplicationTests {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ApartmentRepository apartmentRepository;

    @Test
    void testApartmentsInsert() {

        Apartment apartment1 = new Apartment();
        apartment1.setArea(5);
        apartment1.setAirconditioning("yes");
        // ....
        apartmentRepository.save(apartment1);
        System.out.println("Apartment saved: " + apartment1);

        // Display all apartments in the database
        int index = 0;
        System.out.println("\n=== Apartments in the Database ===");
        for (Apartment apartment : apartmentRepository.findAll()) {
            index++;
            System.out.println("#" + index);
            System.out.println(apartment);
        }

}
