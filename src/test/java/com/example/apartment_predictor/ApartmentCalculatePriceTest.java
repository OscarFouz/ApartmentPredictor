package com.example.apartment_predictor;


import com.example.apartment_predictor.model.Apartment;
import com.example.apartment_predictor.model.Duplex;
import com.example.apartment_predictor.model.House;
import com.example.apartment_predictor.model.TownHouse;
import org.junit.jupiter.api.Test;

public class ApartmentCalculatePriceTest {

    @Test
    void testCalculatePrice() {
        Apartment apartment = new Apartment();
        apartment.setArea(100);
        apartment.setBedrooms(3);
        apartment.setLocationRating(15);
        apartment.setPrice(300L);

        System.out.println(apartment);

        System.out.println("Calculate price Apartment: " + apartment.calculatePrice());

        House house = new House();
        house.setArea(251);
        house.setBedrooms(5);
        house.setLocationRating(32);

        System.out.println(house);

        System.out.println("Calculate price House: " + house.calculatePrice());

        Duplex duplex = new Duplex();
        duplex.setArea(185);
        duplex.setBedrooms(4);
        duplex.setLocationRating(25);

        System.out.println(duplex);

        System.out.println("Calculate price Duplex: " + duplex.calculatePrice());

        TownHouse townHouse = new TownHouse();
        townHouse.setArea(189);
        townHouse.setBedrooms(5);
        townHouse.setHasHomeownersAssociation(true);
        townHouse.setHoaMonthlyFee(150);
        townHouse.setLocationRating(28);

        System.out.println(townHouse);
        System.out.println("Calculate price TownHouse: " + townHouse.calculatePrice());

    }
}