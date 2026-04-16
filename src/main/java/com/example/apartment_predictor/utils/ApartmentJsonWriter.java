package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.Apartment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class ApartmentJsonWriter {

    private static final Logger log = LoggerFactory.getLogger(ApartmentJsonWriter.class);

    public static void writeToFile(List<Apartment> apartments) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("apartments.json"), apartments);

            log.info("Archivo apartments.json generado correctamente");
        } catch (Exception e) {
            log.error("Error al generar apartments.json", e);
        }
    }
}
