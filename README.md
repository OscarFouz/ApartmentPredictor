# ApartmentPredictor — Backend Spring Boot

Sistema completo de gestión inmobiliaria basado en un modelo unificado de propiedades (Property), con soporte para apartamentos, casas, dúplex, adosados, contratos, reseñas, reviewers, propietarios y escuelas cercanas.
Incluye un sistema avanzado de población automática de datos, API REST completa, relaciones JPA complejas y soporte para expansión modular.

## Tecnologías

- Java 21
- Spring Boot 3.2
- Spring Data JPA
- H2 Database (modo archivo)
- Maven
- Lombok
- springdoc OpenAPI (Swagger)
- DataFaker
- API REST con CORS para frontend en Vite (localhost:5173)

## Estructura del Proyecto

```txt
src/main/java/com/example/apartment_predictor
│
├── config/
│   └── OpenApiConfig.java          # Configuración Swagger/OpenAPI
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
├── model/
│   ├── Property.java               # Clase base abstracta
│   ├── Apartment.java
│   ├── House.java
│   ├── Duplex.java
│   ├── TownHouse.java
│   ├── Owner.java                 # Hereda de Person
│   ├── Reviewer.java              # Hereda de Person
│   ├── Person.java                # Clase base abstracta
│   ├── Review.java
│   ├── School.java
│   └── PropertyContract.java
│
├── repository/                     # Spring Data JPA Repositories
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
│   ├── DistanceService.java
│   ├── HaversineService.java
│   └── ReviewerService.java
│
├── graph/                         # Sistema de grafos Manhattan
│   ├── Graph.java
│   ├── Node.java
│   ├── Edge.java
│   ├── AStar.java                # Algoritmo A* para pathfinding
│   └── ManhattanGraphService.java
│
├── utils/
│   ├── PopulateDB.java           # Orquestador de población
│   ├── DataGenerator.java        # Generación de datos
│   ├── DatabaseSeeder.java       # Guardado en BD
│   ├── GraphInitializer.java      # Inicialización del grafo
│   ├── DistanceCalculator.java    # Utilidad Haversine
│   ├── ApartmentJsonWriter.java
│   └── PrintingUtils.java
│
├── dto/
│   └── SchoolDistanceDTO.java
│
└── exception/
    ├── PropertyNotFoundException.java
    └── UnknownPropertyTypeException.java
```

## Modelo de Datos

El sistema utiliza **herencia JPA con SINGLE_TABLE** para unificar todas las propiedades en una sola tabla.

### Property (clase base)
- id, address, owner, nearbySchools (ManyToMany), propertyContracts (OneToMany), reviews (OneToMany)

**Subtipos:** Apartment, House, Duplex, TownHouse

### Apartment
Atributos específicos: name, price, area, bedrooms, bathrooms, stories, mainroad, guestroom, basement, hotwaterheating, airconditioning, parking, prefarea, furnishingstatus

### Owner / Reviewer (heredan de Person)
Atributos de Owner: isBusiness, idLegalOwner, registrationDate, qtyDaysAsOwner
Atributos de Reviewer: reputation, xAccount, webURL, qtyReviews

### Review
Atributos: title, content (Lob), rating, reviewDate

### School
Atributos: name, address, type, educationLevel, location, rating, isPublic, latitude, longitude

### PropertyContract
Atributos: contractName, contractDetails, agreedPrice, startDate, endDate, active

## Población Automática de Datos

La clase `PopulateDB` genera datos sintéticos usando DataFaker:
- **Owners:** genera N propietarios con datos realistas
- **Properties:** 40% Apartments, 20% Houses, 20% Duplex, 20% TownHouse
- **Schools:** escuelas reales de Manhattan con coordenadas GPS
- **Reviewers:** generate revisores con reputación
- **Reviews:** genera reseñas y las asocia a propiedades
- **Contracts:** genera contratos y los asocia a owners y properties

## API REST

### Endpoints Principales

| Recurso | Endpoints |
|---------|-----------|
| Apartments | GET, POST /api/apartments, GET/PUT/DELETE /api/apartments/{id} |
| Houses | GET, POST /api/houses, GET/PUT/DELETE /api/houses/{id} |
| Duplexes | GET, POST /api/duplexes, GET/PUT/DELETE /api/duplexes/{id} |
| TownHouses | GET, POST /api/townhouses, GET/PUT/DELETE /api/townhouses/{id} |
| Owners | GET, POST /api/owners, GET/PUT/DELETE /api/owners/{id} |
| Reviewers | GET, POST /api/reviewers, GET/PUT/DELETE /api/reviewers/{id} |
| Reviews | GET/POST /api/reviews/property/{id} |
| Contracts | GET, POST /api/contracts, PUT /api/contracts/{id}/close |
| Schools | GET /api/schools |
| Distances | GET /api/distance/{propertyId}/schools |

### Documentación API (Swagger)

Una vez iniciada la aplicación, accede a:
- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

## Sistema de Grafos Manhattan

El proyecto incluye un sistema de grafos para calcular distancias en Manhattan:
- **Nodos:** 8 intersecciones en Manhattan con coordenadas reales
- **Aristas:** conexiones entre nodos con distancias Haversine
- **Algoritmo A\*:** implementación para encontrar el path más corto

## Configuración

### application.properties
```properties
app.populate-on-start=false
spring.datasource.url=jdbc:h2:file:./db/apartments
spring.h2.console.enabled=true
```

### Variables de Entorno
- `app.populate-on-start`: Enable/disable población automática al iniciar

## Ejecución

```bash
# Compilar
./mvnw clean compile

# Ejecutar
./mvnw spring-boot:run

# O desde IDE (IntelliJ)
# Run ApartmentPredictorApplication
```

**Accesos:**
- API REST: http://localhost:8080/api/*
- H2 Console: http://localhost:8080/h2-console
- Swagger UI: http://localhost:8080/swagger-ui.html

## Mejoras Implementadas

- [x] Batch operations con `saveAll()` para mejor rendimiento
- [x] Extracción de código duplicado (DistanceCalculator)
- [x] Refactorización de PopulateDB (DataGenerator, DatabaseSeeder, GraphInitializer)
- [x] Excepciones custom (PropertyNotFoundException, UnknownPropertyTypeException)
- [x] Uso de Optional<Double> en A*
- [x] Lombok @Data para reducir boilerplate
- [x] SLF4J Logging替换 System.out
- [x] Paginación en queries de escuelas
- [x] OpenAPI/Swagger para documentación
- [x] Javadoc en clases principales

## Autor

Proyecto desarrollado por Óscar.

Backend modular, escalable y preparado para integrarse con frontend en Vite/React.
