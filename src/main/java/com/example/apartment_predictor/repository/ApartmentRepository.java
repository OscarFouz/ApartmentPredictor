package com.example.apartment_predictor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.apartment_predictor.model.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, String> {
    
    @Query("SELECT a FROM Apartment a WHERE " +
           "(:minPrice IS NULL OR a.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR a.price <= :maxPrice) AND " +
           "(:minArea IS NULL OR a.area >= :minArea) AND " +
           "(:maxArea IS NULL OR a.area <= :maxArea) AND " +
           "(:minBedrooms IS NULL OR a.bedrooms >= :minBedrooms) AND " +
           "(:maxBedrooms IS NULL OR a.bedrooms <= :maxBedrooms)")
    Page<Apartment> findByFilters(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea,
            @Param("minBedrooms") Integer minBedrooms,
            @Param("maxBedrooms") Integer maxBedrooms,
            Pageable pageable);
}
