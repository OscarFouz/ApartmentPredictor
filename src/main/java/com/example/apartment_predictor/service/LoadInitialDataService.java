package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.repository.ApartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LoadInitialDataService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    public void importApartmentsFromCSV(String csvPath) throws IOException {

        Path path = Path.of(csvPath);

        try (BufferedReader br = Files.newBufferedReader(path)) {

            String line = br.readLine(); // saltar cabecera

            while ((line = br.readLine()) != null) {

                String[] v = line.split(",");

                Apartment apt = new Apartment();

                apt.setPrice(Long.parseLong(v[0]));
                apt.setArea(Integer.parseInt(v[1]));
                apt.setBedrooms(Integer.parseInt(v[2]));
                apt.setBathrooms(Integer.parseInt(v[3]));
                apt.setStories(Integer.parseInt(v[4]));
                apt.setMainroad(v[5]);
                apt.setGuestroom(v[6]);
                apt.setBasement(v[7]);
                apt.setHotwaterheating(v[8]);
                apt.setAirconditioning(v[9]);
                apt.setParking(Integer.parseInt(v[10]));
                apt.setPrefarea(v[11]);
                apt.setFurnishingstatus(v[12]);

                apt.setLocationRating(3);

                apartmentRepository.save(apt);
            }
        }
    }
}
