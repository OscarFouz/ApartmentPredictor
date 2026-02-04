package com.example.apartment_predictor.utils;

import com.example.apartment_predictor.model.Apartment;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;

public class ApartmentJsonWriter {

    public static void writeToFile(List<Apartment> apartments) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("apartments.json"), apartments);

            System.out.println("Archivo apartments.json generado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
