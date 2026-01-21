package com.example.apartment_predictor;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Review;
import com.example.apartment_predictor.repository.ApartmentRepository;
import com.example.apartment_predictor.repository.ReviewRepository;
import com.example.apartment_predictor.service.LoadInitialDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.file.Path; import java.nio.file.Paths;


@SpringBootApplication
public class ApartmentPredictorApplication implements CommandLineRunner {

    @Value("${app.csv.path}")
    private String csvPath;


    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private LoadInitialDataService loadInitialDataService;


    public static void main(String[] args) {

        SpringApplication.run(ApartmentPredictorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


   // Solo insertar si la tabla está vacía
        if (apartmentRepository.count() == 0) {
            System.out.println("Base de datos vacía. Insertando apartamentos iniciales...");

            Path path = Paths.get(csvPath);
            loadInitialDataService.importApartmentsFromCSV(csvPath);

            System.out.println("Importación completada.");
        }

    else {
        System.out.println("La base de datos ya tiene datos. No se insertan apartamentos.");
    }

    if (reviewRepository.count() == 0) {
        System.out.println("Base de datos vacía. Insertando reviews iniciales...");
        //testReviewsInsert();
    }
    else {
        System.out.println("La base de datos ya tiene datos. No se insertan reviews.");
    }

    // Mantener la aplicación abierta
    System.out.println("Aplicación iniciada. Servidor activo.");
    Thread.currentThread().join();
    //apartmentRepository.findAll().forEach(System.out::println);
    }
}