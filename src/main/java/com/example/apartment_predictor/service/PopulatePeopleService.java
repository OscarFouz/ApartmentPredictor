package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.Owner;
import com.example.apartment_predictor.model.Reviewer;
import com.example.apartment_predictor.repository.OwnerRepository;
import com.example.apartment_predictor.repository.ReviewerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class PopulatePeopleService {

    private final OwnerRepository ownerRepository;
    private final ReviewerRepository reviewerRepository;
    private final Random random = new Random();

    public PopulatePeopleService(OwnerRepository ownerRepository,
                                 ReviewerRepository reviewerRepository) {
        this.ownerRepository = ownerRepository;
        this.reviewerRepository = reviewerRepository;
    }

    public void populateOwnersAndReviewers() {

        if (ownerRepository.count() > 0) return;

        for (int i = 1; i <= 15; i++) {

            String name = "User" + i;
            String email = "user" + i + "@mail.com";

            Owner owner = new Owner(
                    name,
                    email,
                    25 + random.nextInt(30),
                    random.nextBoolean(),
                    random.nextBoolean(),
                    "LEGAL-" + i,
                    LocalDate.now().minusDays(random.nextInt(1000)),
                    random.nextInt(1000)
            );

            ownerRepository.save(owner);

            Reviewer reviewer = new Reviewer();
            reviewer.setName(name);
            reviewer.setSurname("Surname" + i);
            reviewer.setEmail(email);
            reviewer.setReputation(random.nextInt(500));

            reviewerRepository.save(reviewer);
        }
    }
}
