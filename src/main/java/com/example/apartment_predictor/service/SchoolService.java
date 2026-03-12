package com.example.apartment_predictor.service;

import com.example.apartment_predictor.model.School;
import com.example.apartment_predictor.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    public Iterable<School> findAll() {
        return schoolRepository.findAll();
    }

    public School save(School school) {
        return schoolRepository.save(school);
    }

    public School updateById(String id, School newSchool) {
        Optional<School> existingOpt = schoolRepository.findById(id);
        if (existingOpt.isEmpty()) return null;

        School existing = existingOpt.get();

        existing.setName(newSchool.getName());
        existing.setAddress(newSchool.getAddress());
        existing.setType(newSchool.getType());
        existing.setEducationLevel(newSchool.getEducationLevel());
        existing.setLocation(newSchool.getLocation());
        existing.setRating(newSchool.getRating());
        existing.setPublic(newSchool.isPublic());

        return schoolRepository.save(existing);
    }

    public void delete(String id) {
        schoolRepository.deleteById(id);
    }

    public School findById(String id) {
        return schoolRepository.findById(id).orElse(null);
    }
}
