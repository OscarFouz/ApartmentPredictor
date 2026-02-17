package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.*;
import com.example.apartment_predictor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.apartment_predictor.repository.OwnerRepository;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

@Service
public class LoadInitialDataService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyContractService contractService;

    private final Random random = new Random();

    public void importApartmentsFromCSV(String csvPath) throws IOException {

        if (apartmentRepository.count() > 0) return;

        InputStream is = getClass().getClassLoader().getResourceAsStream(csvPath);
        if (is == null) throw new IOException("No se encontró CSV: " + csvPath);

        List<Owner> owners = (List<Owner>) ownerRepository.findAll();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            br.readLine(); // cabecera

            String line;
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
                apt.setLocationRating(random.nextInt(5) + 1);

                apartmentRepository.save(apt);

                int type = random.nextInt(4) + 1;

                switch (type) {
                    case 2 -> {
                        House h = new House();
                        h.setApartment(apt);
                        h.setGarageQty(random.nextInt(3));
                        h.setRoofType(randomRoof());
                        h.setGarden(randomYesNo());
                        houseRepository.save(h);
                    }
                    case 3 -> {
                        Duplex d = new Duplex();
                        d.setApartment(apt);
                        d.setBalcony(randomYesNo());
                        d.setElevator(random.nextBoolean());
                        d.setHasSeparateUtilities(random.nextBoolean());
                        duplexRepository.save(d);
                    }
                    case 4 -> {
                        TownHouse th = new TownHouse();
                        th.setApartment(apt);
                        th.setHasHomeownersAssociation(random.nextBoolean());
                        th.setHoaMonthlyFee(random.nextInt(200) + 50);
                        townHouseRepository.save(th);
                    }
                }

                if (!owners.isEmpty()) {
                    Owner randomOwner = owners.get(random.nextInt(owners.size()));
                    contractService.createContract(randomOwner, apt, apt.getPrice());
                }
            }
        }
    }

    private String randomRoof() {
        return new String[]{"tile", "flat", "wood", "metal"}[random.nextInt(4)];
    }

    private String randomYesNo() {
        return random.nextBoolean() ? "yes" : "no";
    }
}
