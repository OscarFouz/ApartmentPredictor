# MANUAL TÉCNICO
Proyecto: ApartmentPredictor
Tecnologías: Spring Boot 3.2, Java 21, JPA/Hibernate, H2 Database, Maven, Lombok, OpenAPI (springdoc)

---

# 1. Introducción

ApartmentPredictor es un backend modular diseñado para gestionar un ecosistema inmobiliario completo basado en un modelo unificado de propiedades (Property).
El sistema soporta:

- Apartamentos
- Casas
- Dúplex
- TownHouses
- Propietarios
- Contratos
- Reseñas
- Reviewers
- Escuelas cercanas

Incluye un sistema avanzado de **población automática de datos**, API REST completa, relaciones JPA complejas, herencia con SINGLE_TABLE, un sistema de grafos Manhattan para cálculo de distancias, y documentación OpenAPI/Swagger.

---

# 2. Arquitectura General del Proyecto

El proyecto sigue una arquitectura en capas:

- **Controller** → expone la API REST
- **Service** → lógica de negocio
- **Repository** → acceso a datos con Spring Data JPA
- **Model** → entidades JPA (con Lombok @Data)
- **Config** → configuración de Swagger/OpenAPI
- **DTO** → objetos de transferencia de datos
- **Exception** → excepciones custom
- **Graph** → sistema de grafos Manhattan (A*, Haversine)
- **Utils** → utilidades y generación de datos

Estructura:

```txt
src/main/java/com/example/apartment_predictor
│
├── config/
│   └── OpenApiConfig.java              # Configuración Swagger
│
├── controller/
│   ├── ApartmentController.java
│   ├── HouseController.java
│   ├── DuplexController.java
│   ├── TownHouseController.java
│   ├── OwnerController.java
│   ├── ReviewerController.java
│   ├── ReviewController.java
│   ├── PropertyContractController.java
│   ├── DistanceController.java
│   ├── SchoolController.java
│   └── PopulateDBController.java
│
├── dto/
│   └── SchoolDistanceDTO.java           # DTO para distancias
│
├── exception/
│   ├── PropertyNotFoundException.java
│   └── UnknownPropertyTypeException.java
│
├── graph/                              # Sistema de grafos Manhattan
│   ├── Graph.java
│   ├── Node.java
│   ├── Edge.java
│   ├── AStar.java                     # Algoritmo A*
│   └── ManhattanGraphService.java
│
├── model/
│   ├── Property.java                   # Clase base abstracta (@Data)
│   ├── Apartment.java
│   ├── House.java
│   ├── Duplex.java
│   ├── TownHouse.java
│   ├── Owner.java                     # Hereda de Person
│   ├── Reviewer.java                  # Hereda de Person
│   ├── Person.java                    # Clase base abstracta (@Data)
│   ├── Review.java
│   ├── School.java
│   └── PropertyContract.java
│
├── repository/
│   └── (JpaRepositories para cada entidad)
│
├── service/
│   ├── ApartmentService.java
│   ├── HouseService.java
│   ├── DuplexService.java
│   ├── TownHouseService.java
│   ├── OwnerService.java
│   ├── ReviewerService.java
│   ├── ReviewService.java
│   ├── PropertyService.java
│   ├── PropertyContractService.java
│   ├── DistanceService.java           # Servicio de distancias
│   └── HaversineService.java          # Cálculo Haversine
│
└── utils/
    ├── PopulateDB.java                # Orquestador de población
    ├── DataGenerator.java             # Generación de datos
    ├── DatabaseSeeder.java            # Guardado en BD
    ├── GraphInitializer.java          # Inicialización del grafo
    ├── DistanceCalculator.java        # Utilidad Haversine
    ├── ApartmentJsonWriter.java
    └── PrintingUtils.java
```

---

# 3. Modelo de Datos

El sistema utiliza **herencia JPA con SINGLE_TABLE**, lo que permite almacenar todas las propiedades en una única tabla con una columna discriminadora.

## 3.1 Property (clase base)

Atributos principales (generados por Lombok @Data):
- id
- address
- price
- latitude, longitude (coordenadas GPS)
- nearestNodeId (nodo más cercano en el grafo Manhattan)
- owner
- nearbySchools (ManyToMany)
- propertyContracts (OneToMany)
- reviews (OneToMany)

Es la base para:
- Apartment
- House
- Duplex
- TownHouse

## 3.2 Apartment

Atributos específicos:
- name
- price, area, bedrooms, bathrooms, stories
- mainroad, guestroom, basement, hotwaterheating, airconditioning
- parking, prefarea, furnishingstatus

## 3.3 House / Duplex / TownHouse

Atributos:
- name
- address heredado

## 3.4 Owner (hereda de Person)

Atributos:
- isBusiness
- idLegalOwner
- registrationDate
- qtyDaysAsOwner

## 3.5 Reviewer (hereda de Person)

Atributos:
- reputation
- isBusiness
- xAccount
- webURL
- qtyReviews

## 3.6 Review

Atributos:
- title
- content (Lob)
- rating
- reviewDate

## 3.7 School

Atributos:
- name, address
- type, educationLevel
- location
- rating
- isPublic
- latitude, longitude (coordenadas GPS)
- nearestNodeId

## 3.8 PropertyContract

Atributos:
- contractName
- contractDetails
- agreedPrice
- startDate, endDate
- active

---

# 4. Repositorios

Todos los repositorios extienden JpaRepository.

```java
public interface ApartmentRepository extends JpaRepository<Apartment, String> {}
public interface SchoolRepository extends JpaRepository<School, String> {
    List<School> findTop10By(Pageable pageable);  // Paginación para mejor rendimiento
}
```

---

# 5. Servicios

## 5.1 ApartmentService / HouseService / DuplexService / TownHouseService

Funciones:
- findAll
- findById
- update
- updateById
- delete

## 5.2 DistanceService

Servicio para calcular distancias entre propiedades y escuelas:
- getSchoolsWithDistances(propertyId) → lista de escuelas con distancias Haversine y Manhattan

## 5.3 HaversineService / ManhattanGraphService

- HaversineService: cálculo directo de distancia Haversine
- ManhattanGraphService: sistema de grafos con algoritmo A* para pathfinding Manhattan

## 5.4 PropertyService

Servicio unificado para buscar cualquier tipo de propiedad por ID.

## 5.5 PopulateDB (Sistema de Generación)

Orquestador que utiliza:
- **DataGenerator**: genera datos sintéticos (owners, properties, schools, reviewers, reviews, contracts)
- **DatabaseSeeder**: guarda datos en BD usando saveAll() batch
- **GraphInitializer**: inicializa el grafo Manhattan

---

# 6. Controladores REST

## 6.1 ApartmentController
- GET /api/apartments
- GET /api/apartments/{id}
- POST /api/apartments
- PUT /api/apartments/{id}
- DELETE /api/apartments/{id}
- PUT /api/apartments/{id}/assign-schools

## 6.2 HouseController / DuplexController / TownHouseController
CRUD completo para cada tipo.

## 6.3 OwnerController
- CRUD
- GET /api/owners/{id}/houses
- GET /api/owners/{id}/duplexes
- GET /api/owners/{id}/townhouses

## 6.4 ReviewerController
- CRUD
- GET /api/reviewers/{id}/reviews

## 6.5 ReviewController
- GET /api/reviews/property/{id}
- POST /api/reviews/property/{id}
- DELETE /api/reviews/{reviewId}

## 6.6 PropertyContractController
- GET /api/contracts
- GET /api/contracts/{id}
- POST /api/contracts
- PUT /api/contracts/{id}/close
- DELETE /api/contracts/{id}

## 6.7 DistanceController
- GET /api/distance/{propertyId}/schools

## 6.8 SchoolController
- GET /api/schools

## 6.9 PopulateDBController
- GET /api/populate

---

# 7. Sistema de Grafos Manhattan

El proyecto incluye un sistema de grafos para calcular distancias en Manhattan:
- **Graph**: estructura de datos para nodos y aristas
- **Node**: representa una intersección con lat/lon
- **Edge**: conexión entre nodos con distancia Haversine
- **AStar**: implementación del algoritmo A* para pathfinding
- **ManhattanGraphService**: servicio que usa el grafo para calcular distancias

---

# 8. Excepciones Custom

- **PropertyNotFoundException**: cuando no se encuentra una propiedad
- **UnknownPropertyTypeException**: cuando el tipo de propiedad no es reconocido

---

# 9. Documentación API (OpenAPI/Swagger)

Una vez iniciada la aplicación:
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

---

# 10. Configuración del Sistema

Archivo `application.properties`:

```properties
spring.datasource.url=jdbc:h2:file:./db/apartments
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
app.populate-on-start=false
```

---

# 11. Estado del Proyecto

- Backend completamente funcional
- Modelo unificado Property con herencia JPA SINGLE_TABLE
- Relaciones complejas entre entidades
- Sistema de grafos Manhattan con A*
- Población automática con batch operations (saveAll)
- API REST documentada con Swagger/OpenAPI
- Arquitectura modular y escalable
- Uso de Lombok para reducir boilerplate
- SLF4J Logging para mejor trazabilidad

---
