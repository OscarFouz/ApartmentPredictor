package com.example.apartment_predictor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.apartment_predictor.model.House;

@Repository
public interface HouseRepository extends JpaRepository<House, String> {
    
    @Query("SELECT h FROM House h WHERE " +
           "(:minPrice IS NULL OR h.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR h.price <= :maxPrice) AND " +
           "(:minArea IS NULL OR h.area >= :minArea) AND " +
           "(:maxArea IS NULL OR h.area <= :maxArea) AND " +
           "(:minBedrooms IS NULL OR h.bedrooms >= :minBedrooms) AND " +
           "(:maxBedrooms IS NULL OR h.bedrooms <= :maxBedrooms)")
    Page<House> findByFilters(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea,
            @Param("minBedrooms") Integer minBedrooms,
            @Param("maxBedrooms") Integer maxBedrooms,
            Pageable pageable);
}
