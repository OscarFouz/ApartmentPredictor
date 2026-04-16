package com.example.apartment_predictor.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class PropertyContract {

    @Id
    private String id;

    private String contractName;
    private String contractDetails;
    private double agreedPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference("owner-contracts")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "property_id")
    @JsonBackReference("property-contracts")
    private Property property;

    public PropertyContract() {
        this.id = UUID.randomUUID().toString();
    }

    public PropertyContract(String contractName, String contractDetails,
                            double agreedPrice, LocalDate startDate,
                            Owner owner, Property property) {
        this.id = UUID.randomUUID().toString();
        this.contractName = contractName;
        this.contractDetails = contractDetails;
        this.agreedPrice = agreedPrice;
        this.startDate = startDate;
        this.active = true;
        this.owner = owner;
        this.property = property;
    }
}
