package com.example.apartment_predictor.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("OWNER")
public class Owner extends Person {

    private String email;
    private int age;
    private boolean isActive;
    private boolean isBusiness;
    private String idLegalOwner;
    private LocalDate registrationDate;
    private int qtyDaysAsOwner;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<PropertyContract> contracts = new ArrayList<>();

    public Owner() {}

    public Owner(String name, String email, int age, boolean isActive, boolean isBusiness,
                 String idLegalOwner, LocalDate registrationDate, int qtyDaysAsOwner) {

        this.name = name; // heredado de Person
        this.email = email;
        this.age = age;
        this.isActive = isActive;
        this.isBusiness = isBusiness;
        this.idLegalOwner = idLegalOwner;
        this.registrationDate = registrationDate;
        this.qtyDaysAsOwner = qtyDaysAsOwner;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isBusiness() { return isBusiness; }
    public void setBusiness(boolean business) { isBusiness = business; }

    public String getIdLegalOwner() { return idLegalOwner; }
    public void setIdLegalOwner(String idLegalOwner) { this.idLegalOwner = idLegalOwner; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public int getQtyDaysAsOwner() { return qtyDaysAsOwner; }
    public void setQtyDaysAsOwner(int qtyDaysAsOwner) { this.qtyDaysAsOwner = qtyDaysAsOwner; }

    public List<PropertyContract> getContracts() { return contracts; }

    public void addContract(PropertyContract contract) {
        contracts.add(contract);
        contract.setOwner(this);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                ", isBusiness=" + isBusiness +
                ", idLegalOwner='" + idLegalOwner + '\'' +
                ", registrationDate=" + registrationDate +
                ", qtyDaysAsOwner=" + qtyDaysAsOwner +
                '}';
    }
}
