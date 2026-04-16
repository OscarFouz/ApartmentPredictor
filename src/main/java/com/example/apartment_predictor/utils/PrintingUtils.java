package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.repository.ApartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintingUtils {

    private static final Logger log = LoggerFactory.getLogger(PrintingUtils.class);

    @Autowired
    private ApartmentRepository apartmentRepository;

    public static void printList(List list) {
        for (Object obj : list) {
            log.info("{}", obj);
        }
    }

    public static void printList(List list, String title) {
        log.info("\n=== {} ===", title);
        for (Object obj : list) {
            log.info("{}", obj);
        }
    }

    public static void printApartments(CrudRepository apartmentRepository) {
        int index = 0;
        log.info("\n=== Apartments in the Database ===");
        for (Object apartment : apartmentRepository.findAll()) {
            index++;
            log.info("#{}", index);
            log.info("{}", apartment);
        }
    }

    public static void printApartmentsList(Iterable<Apartment> apartments) {
        int index = 0;
        log.info("\n=== Apartments in the Database ===");
        for (Apartment apartment : apartments) {
            index++;
            log.info("#{}", index);
            log.info("{}", apartment);
        }
    }

    public static void printObjectsList(Iterable<?> list) {
        int index = 0;
        log.info("\n=== Apartments in the Database ===");
        for (Object obj : list) {
            index++;
            log.info("#{}", index);
            log.info("{}", obj);
        }
    }

    public void printApartmentsByRepoInstance() {
        int index = 0;
        log.info("\n=== Apartments in the Database ===");
        for (Apartment apartment : apartmentRepository.findAll()) {
            index++;
            log.info("#{}", index);
            log.info("{}", apartment);
        }
    }

}
