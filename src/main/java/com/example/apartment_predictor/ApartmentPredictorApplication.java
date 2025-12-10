package com.example.apartment_predictor;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ApartmentRepository;
import com.example.apartment_predictor.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class ApartmentPredictorApplication implements CommandLineRunner {

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApartmentPredictorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Creating apartment objects...");

        testApartmentsInsert();
        testReviewsInsert();

    }


    private void printApartmentDetails(Apartment apartment, String description) {
        System.out.println("\n" + description + ":");
        System.out.println("  Price: $" + apartment.getPrice());
        System.out.println("  Area: " + apartment.getArea() + " sq ft");
        System.out.println("  Bedrooms: " + apartment.getBedrooms());
        System.out.println("  Bathrooms: " + apartment.getBathrooms());
        System.out.println("  Stories: " + apartment.getStories());
        System.out.println("  Main Road Access: " + apartment.getMainroad());
        System.out.println("  Guest Room: " + apartment.getGuestroom());
        System.out.println("  Basement: " + apartment.getBasement());
        System.out.println("  Hot Water Heating: " + apartment.getHotwaterheating());
        System.out.println("  Air Conditioning: " + apartment.getAirconditioning());
        System.out.println("  Parking Spaces: " + apartment.getParking());
        System.out.println("  Preferred Area: " + apartment.getPrefarea());
        System.out.println("  Furnishing Status: " + apartment.getFurnishingstatus());
    }

    public void testInsert() {
        // Create apartment objects based on your sample data
        Apartment apartment1 = new Apartment(
                13300000L,    // price
                7420,         // area
                4,            // bedrooms
                2,            // bathrooms
                3,            // stories
                "yes",        // mainroad
                "no",         // guestroom
                "no",         // basement
                "no",         // hotwater
                "yes",        // airconditioning
                2,            // parking
                "yes",        // prefarea
                "furnished"   // furnishingstatus
        );

        // Create additional sample apartments
        Apartment apartment2 = new Apartment(
                8500000L,     // price
                5200,         // area
                3,            // bedrooms
                2,            // bathrooms
                2,            // stories
                "yes",        // mainroad
                "yes",        // guestroom
                "no",         // basement
                "yes",        // hotwater
                "yes",        // airconditioning
                1,            // parking
                "no",         // prefarea
                "semi-furnished" // furnishingstatus
        );

        Apartment apartment3 = new Apartment(
                6200000L,     // price
                3800,         // area
                2,            // bedrooms
                1,            // bathrooms
                1,            // stories
                "no",         // mainroad
                "no",         // guestroom
                "yes",        // basement
                "yes",        // hotwater
                "no",         // airconditioning
                0,            // parking
                "yes",        // prefarea
                "unfurnished" // furnishingstatus
        );

        // Display the created apartments
        //System.out.println("\n=== Created Apartments ===");
        //System.out.println("Apartment 1: " + apartment1);
        //System.out.println("\nApartment 2: " + apartment2);
        //System.out.println("\nApartment 3: " + apartment3);

        //System.out.println("\n=== Apartment Details ===");
        //printApartmentDetails(apartment1, "Luxury Apartment");
        //printApartmentDetails(apartment2, "Family Apartment");
        //printApartmentDetails(apartment3, "Budget Apartment");

        apartmentRepository.save(apartment1);
        apartmentRepository.save(apartment2);
        apartmentRepository.save(apartment3);

        int index = 0;
        System.out.println("\n=== Apartments in the Database ===");
        for (Apartment apartment : apartmentRepository.findAll()){
            index++;
            System.out.println("#" + index);
            System.out.println(apartment);
        }

        //apartmentRepository.findAll().forEach(System.out::println);
    }
    public void testReviewsInsert() {
        // Create review objects based on sample data
        Review review1 = new Review();
        review1.setId(java.util.UUID.randomUUID().toString());
        review1.setTitle("Amazing apartment with great location");
        review1.setContent("This apartment exceeded my expectations. The location is perfect and the amenities are top-notch. Highly recommended for anyone looking for a comfortable stay.");
        review1.setRating(5);
        review1.setReviewDate(LocalDate.of(2024, 1, 15));

        Review review2 = new Review();
        review2.setId(java.util.UUID.randomUUID().toString());
        review2.setTitle("Good value for money");
        review2.setContent("Nice apartment overall. Clean and well-maintained. The only downside is the parking situation but everything else was great.");
        review2.setRating(4);
        review2.setReviewDate(LocalDate.of(2024, 2, 3));

        Review review3 = new Review();
        review3.setId(java.util.UUID.randomUUID().toString());
        review3.setTitle("Disappointing experience");
        review3.setContent("The apartment looked much better in photos. Several issues with plumbing and the heating system didn't work properly during our stay.");
        review3.setRating(2);
        review3.setReviewDate(LocalDate.of(2024, 2, 18));

        Review review4 = new Review();
        review4.setId(java.util.UUID.randomUUID().toString());
        review4.setTitle("Perfect for families");
        review4.setContent("Spacious apartment with excellent facilities for children. The playground nearby and safe neighborhood make it ideal for families with kids.");
        review4.setRating(5);
        review4.setReviewDate(LocalDate.of(2024, 3, 5));

        Review review5 = new Review();
        review5.setId(java.util.UUID.randomUUID().toString());
        review5.setTitle("Average stay");
        review5.setContent("Nothing special but nothing terrible either. Basic amenities and decent location. Would consider staying again if the price is right.");
        review5.setRating(3);
        review5.setReviewDate(LocalDate.of(2024, 3, 22));

        // Save reviews to database
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);
        reviewRepository.save(review5);

        // Display all reviews in the database
        int index = 0;
        System.out.println("\n=== Reviews in the Database ===");
        for (Review review : reviewRepository.findAll()) {
            index++;
            System.out.println("#" + index);
            System.out.println(review);
        }
    }

    public void testApartmentsInsert() {
        // Create review objects based on sample data
        Apartment apartment1 = new Apartment();
        //apartment1.setId(java.util.UUID.randomUUID().toString());
        apartment1.setPrice(325621L);
        apartment1.setArea(2365);
        apartment1.setBedrooms(4);
        apartment1.setBathrooms(5);
        apartment1.setStories(3);
        apartment1.setMainroad("yes");
        apartment1.setGuestroom("yes");
        apartment1.setBasement("yes");
        apartment1.setHotwaterheating("yes");
        apartment1.setAirconditioning("yes");
        apartment1.setParking(3);
        apartment1.setPrefarea("yes");
        apartment1.setFurnishingstatus("yes");



        Apartment apartment2 = new Apartment();
        //apartment2.setId(java.util.UUID.randomUUID().toString());
        apartment2.setPrice(58423L);
        apartment2.setArea(32);
        apartment2.setBedrooms(1);
        apartment2.setBathrooms(1);
        apartment2.setStories(5);
        apartment2.setMainroad("yes");
        apartment2.setGuestroom("yes");
        apartment2.setBasement("yes");
        apartment2.setHotwaterheating("yes");
        apartment2.setAirconditioning("yes");
        apartment2.setParking(0);
        apartment2.setPrefarea("yes");
        apartment2.setFurnishingstatus("yes");

        Apartment apartment3 = new Apartment();
        //apartment3.setId(java.util.UUID.randomUUID().toString());
        apartment3.setPrice(741852L);
        apartment3.setArea(6666);
        apartment3.setBedrooms(12);
        apartment3.setBathrooms(16);
        apartment3.setStories(5);
        apartment3.setMainroad("yes");
        apartment3.setGuestroom("yes");
        apartment3.setBasement("yes");
        apartment3.setHotwaterheating("yes");
        apartment3.setAirconditioning("yes");
        apartment3.setParking(12);
        apartment3.setPrefarea("yes");
        apartment3.setFurnishingstatus("yes");



        // Save reviews to database
        apartmentRepository.save(apartment1);
        apartmentRepository.save(apartment2);
        apartmentRepository.save(apartment3);


        // Display all reviews in the database
        int index = 0;
        System.out.println("\n=== Reviews in the Database ===");
        for (Apartment apartment : apartmentRepository.findAll()) {
            index++;
            System.out.println("#" + index);
            System.out.println(apartment);
        }
    }

}
