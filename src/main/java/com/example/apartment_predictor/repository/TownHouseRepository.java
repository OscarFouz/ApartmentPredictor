package com.example.apartment_predictor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.apartment_predictor.model.TownHouse;

@Repository
public interface TownHouseRepository extends JpaRepository<TownHouse, String> {
    
    @Query("SELECT t FROM TownHouse t WHERE " +
           "(:minPrice IS NULL OR t.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR t.price <= :maxPrice) AND " +
           "(:minArea IS NULL OR t.area >= :minArea) AND " +
           "(:maxArea IS NULL OR t.area <= :maxArea) AND " +
           "(:minBedrooms IS NULL OR t.bedrooms >= :minBedrooms) AND " +
           "(:maxBedrooms IS NULL OR t.bedrooms <= :maxBedrooms)")
    Page<TownHouse> findByFilters(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea,
            @Param("minBedrooms") Integer minBedrooms,
            @Param("maxBedrooms") Integer maxBedrooms,
            Pageable pageable);
}
