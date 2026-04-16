package com.example.apartment_predictor.repository;

import com.example.apartment_predictor.model.School;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {
    List<School> findTop10By(Pageable pageable);
}
