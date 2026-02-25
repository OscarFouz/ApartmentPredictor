```md
# ApartmentPredictor — Backend Spring Boot
Sistema completo de gestión inmobiliaria basado en un modelo unificado de propiedades (Property), con soporte para apartamentos, casas, dúplex, adosados, contratos, reseñas, reviewers, propietarios y escuelas cercanas.  
Incluye un sistema avanzado de población automática de datos, API REST completa, relaciones JPA complejas y soporte para expansión modular.

------------------------------------------------------------
# Tecnologías

- Java 21
- Spring Boot 3.2
- Spring Data JPA
- H2 Database (modo archivo)
- Maven
- Jackson
- Faker manual (sin DataFaker)
- API REST con CORS para frontend en Vite (localhost:5173)

------------------------------------------------------------
# Estructura del Proyecto

```txt
src/main/java/com/example/apartment_predictor
│
├── controller
│   ├── ApartmentController.java
│   ├── HouseController.java
│   ├── DuplexController.java
│   ├── TownHouseController.java
│   ├── OwnerController.java
│   ├── ReviewerController.java
│   ├── ReviewController.java
│   ├── PropertyContractController.java
│   └── PopulateDBController.java
│
├── model
│   ├── Property.java
│   ├── Apartment.java
│   ├── House.java
│   ├── Duplex.java
│   ├── TownHouse.java
│   ├── Owner.java
│   ├── Reviewer.java
│   ├── Review.java
│   ├── School.java
│   ├── PropertyContract
│   └── Person.java
│
├── repository
│   ├── ApartmentRepository.java
│   ├── HouseRepository.java
│   ├── DuplexRepository.java
│   ├── TownHouseRepository.java
│   ├── OwnerRepository.java
│   ├── ReviewerRepository.java
│   ├── ReviewRepository.java
│   ├── SchoolRepository.java
│   └── PropertyContractRepository.java
│
├── service
│   ├── ApartmentService.java
│   ├── HouseService.java
│   ├── DuplexService.java
│   ├── TownHouseService.java
│   ├── OwnerService.java
│   ├── ReviewerService.java
│   ├── ReviewService.java
│   ├── PropertyService.java
│   └── PropertyContractService.java
│
└── utils
    ├── PopulateDB
    ├── ApartmentJsonWriter.java
    └── PrintingUtils.java
```

------------------------------------------------------------
# Modelo de Datos

El sistema utiliza **herencia JPA con SINGLE_TABLE** para unificar todas las propiedades en una sola tabla.

## Property (clase base)
Atributos:
- id
- address
- owner
- nearbySchools (ManyToMany)
- propertyContracts (OneToMany)
- reviews (OneToMany)

Subtipos:
- Apartment
- House
- Duplex
- TownHouse

## Apartment
Atributos específicos:
- name
- price, area, bedrooms, bathrooms, stories
- mainroad, guestroom, basement, hotwaterheating, airconditioning
- parking, prefarea, furnishingstatus

## House / Duplex / TownHouse
Atributos:
- name
- address (heredado)

Relaciones:
- ManyToOne → Owner
- ManyToMany → School
- OneToMany → PropertyContract
- OneToMany → Review

## Owner (hereda de Person)
Atributos:
- isBusiness
- idLegalOwner
- registrationDate
- qtyDaysAsOwner

Relaciones:
- OneToMany → PropertyContract
- OneToMany → Apartment
- OneToMany → House
- OneToMany → Duplex
- OneToMany → TownHouse

## Reviewer (hereda de Person)
Atributos:
- reputation
- isBusiness
- xAccount
- webURL
- qtyReviews

Relaciones:
- OneToMany → Review

## Review
Atributos:
- title
- content (Lob)
- rating
- reviewDate

Relaciones:
- ManyToOne → Property
- ManyToOne → Reviewer

## School
Atributos:
- name, address
- type, educationLevel
- location
- rating
- isPublic

Relaciones:
- ManyToMany → Property

## PropertyContract
Atributos:
- contractName
- contractDetails
- agreedPrice
- startDate
- endDate
- active

Relaciones:
- ManyToOne → Owner
- ManyToOne → Property

------------------------------------------------------------
# Población Automática de Datos

La clase PopulateDB genera datos completamente sintéticos sin DataFaker:

### Owners
- Genera N owners con:
  - nombre aleatorio
  - email
  - teléfono
  - datos legales
  - fechas y métricas

### Properties
Genera propiedades en proporción:
- 40% Apartments
- 20% Houses
- 20% Duplex
- 20% TownHouse

Cada propiedad:
- se asigna a un Owner
- recibe atributos aleatorios
- se guarda en su repositorio correspondiente

### Schools
- Genera N escuelas
- Se asignan aleatoriamente a cada propiedad (1–3 por propiedad)

### Reviewers
- Genera reviewers con:
  - reputación
  - redes sociales
  - actividad

### Reviews
- Se generan reviews sin asignar
- Luego se asignan:
  - a propiedades
  - a reviewers

### Contracts
- Se generan contratos sin asignar
- Luego se asignan:
  - a un Owner aleatorio
  - a una Property aleatoria

### Resultado final
- Base de datos completamente poblada
- Relaciones consistentes
- Datos variados y realistas

------------------------------------------------------------
# API REST

## Apartments
GET /api/apartments  
GET /api/apartments/{id}  
POST /api/apartments  
PUT /api/apartments/{id}  
DELETE /api/apartments/{id}  
PUT /api/apartments/{id}/assign-schools

## Houses
GET /api/houses  
GET /api/houses/{id}  
POST /api/houses  
PUT /api/houses/{id}  
DELETE /api/houses/{id}

## Duplexes
GET /api/duplexes  
GET /api/duplexes/{id}  
POST /api/duplexes  
PUT /api/duplexes/{id}  
DELETE /api/duplexes/{id}

## TownHouses
GET /api/townhouses  
GET /api/townhouses/{id}  
POST /api/townhouses  
PUT /api/townhouses/{id}  
DELETE /api/townhouses/{id}

## Owners
GET /api/owners  
GET /api/owners/{id}  
POST /api/owners  
PUT /api/owners/{id}  
DELETE /api/owners/{id}  
GET /api/owners/{id}/houses  
GET /api/owners/{id}/duplexes  
GET /api/owners/{id}/townhouses

## Reviewers
GET /api/reviewers  
GET /api/reviewers/{id}  
POST /api/reviewers  
PUT /api/reviewers/{id}  
DELETE /api/reviewers/{id}  
GET /api/reviewers/{id}/reviews

## Reviews
GET /api/reviews/property/{id}  
POST /api/reviews/property/{id}  
DELETE /api/reviews/{reviewId}

## Property Contracts
GET /api/contracts  
GET /api/contracts/{id}  
POST /api/contracts  
PUT /api/contracts/{id}/close  
DELETE /api/contracts/{id}

## Populate Database
GET /api/populate  
Parámetros opcionales:
- owners
- properties
- reviews
- schools

------------------------------------------------------------
# Configuración

Archivo application.properties:

- H2 en modo archivo
- populate-on-start configurable
- rutas CSV configurables
- Hibernate ddl-auto=update

------------------------------------------------------------
# Ejecución

1. Clonar el repositorio
2. Ejecutar con Maven o desde el IDE
3. Acceder a la consola H2:  
   http://localhost:8080/h2-console
4. API disponible en:  
   http://localhost:8080/api/*

------------------------------------------------------------
# Autor

Proyecto desarrollado por Oscar 
Backend modular, escalable y preparado para integrarse con frontend en Vite/React.
```

