package com.example.apartment_predictor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.apartment_predictor.model.Duplex;

@Repository
public interface DuplexRepository extends JpaRepository<Duplex, String> {
    
    @Query("SELECT d FROM Duplex d WHERE " +
           "(:minPrice IS NULL OR d.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR d.price <= :maxPrice) AND " +
           "(:minArea IS NULL OR d.area >= :minArea) AND " +
           "(:maxArea IS NULL OR d.area <= :maxArea) AND " +
           "(:minBedrooms IS NULL OR d.bedrooms >= :minBedrooms) AND " +
           "(:maxBedrooms IS NULL OR d.bedrooms <= :maxBedrooms)")
    Page<Duplex> findByFilters(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea,
            @Param("minBedrooms") Integer minBedrooms,
            @Param("maxBedrooms") Integer maxBedrooms,
            Pageable pageable);
}
