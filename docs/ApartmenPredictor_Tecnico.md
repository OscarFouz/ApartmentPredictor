```md
# MANUAL TÉCNICO  
Proyecto: ApartmentPredictor  
Tecnologías: Spring Boot 3, Java 21, JPA/Hibernate, H2 Database, Maven

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

Incluye un sistema avanzado de **población automática de datos**, API REST completa, relaciones JPA complejas, herencia con SINGLE_TABLE y una arquitectura orientada a servicios.

Este manual describe la arquitectura interna, el modelo de datos, los servicios, los controladores, la configuración y el flujo interno del sistema.

---

# 2. Arquitectura General del Proyecto

El proyecto sigue una arquitectura en capas:

- **Controller** → expone la API REST  
- **Service** → lógica de negocio  
- **Repository** → acceso a datos con Spring Data JPA  
- **Model** → entidades JPA  
- **Utils** → utilidades auxiliares  
- **PopulateDB** → generador de datos sintéticos

Estructura:

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
│   ├── PropertyContractService.java
│   └── PopulateDB.java
│
└── utils
    ├── ApartmentJsonWriter.java
    └── PrintingUtils.java
```

---

# 3. Modelo de Datos

El sistema utiliza **herencia JPA con SINGLE_TABLE**, lo que permite almacenar todas las propiedades en una única tabla con una columna discriminadora.

## 3.1 Property (clase base)

Atributos principales:
- id
- address
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
- price
- area
- bedrooms
- bathrooms
- stories
- mainroad
- guestroom
- basement
- hotwaterheating
- airconditioning
- parking
- prefarea
- furnishingstatus

## 3.3 House / Duplex / TownHouse

Atributos:
- name
- address heredado

Relaciones:
- ManyToOne → Owner
- ManyToMany → School
- OneToMany → PropertyContract
- OneToMany → Review

## 3.4 Owner (hereda de Person)

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

## 3.5 Reviewer (hereda de Person)

Atributos:
- reputation
- isBusiness
- xAccount
- webURL
- qtyReviews

Relaciones:
- OneToMany → Review

## 3.6 Review

Atributos:
- title
- content
- rating
- reviewDate

Relaciones:
- ManyToOne → Property
- ManyToOne → Reviewer

## 3.7 School

Atributos:
- name
- address
- type
- educationLevel
- location
- rating
- isPublic

Relaciones:
- ManyToMany → Property

## 3.8 PropertyContract

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

---

# 4. Repositorios

Todos los repositorios extienden CrudRepository o JpaRepository.

Ejemplos:

```java
public interface ApartmentRepository extends CrudRepository<Apartment, String> {}
public interface SchoolRepository extends JpaRepository<School, String> {}
public interface PropertyContractRepository extends CrudRepository<PropertyContract, String> {}
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

## 5.2 PropertyService

Servicio unificado para buscar cualquier tipo de propiedad por ID.

```java
public Property findById(String id)
```

Busca en:
- ApartmentRepository
- HouseRepository
- DuplexRepository
- TownHouseRepository

## 5.3 ReviewerService

Gestión de reviewers:
- CRUD
- obtener reviews asociadas

## 5.4 ReviewService

Gestión de reviews:
- guardar
- eliminar

## 5.5 PropertyContractService

Gestión de contratos:
- crear contrato
- cerrar contrato
- eliminar contrato
- buscar por ID

## 5.6 PopulateDB

Generador completo de datos sintéticos:
- Owners
- Properties
- Schools
- Reviewers
- Reviews
- Contracts

Incluye asignación de relaciones:
- Schools → Properties
- Reviews → Properties
- Reviews → Reviewers
- Contracts → Owners + Properties

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

## 6.7 PopulateDBController
- GET /api/populate  
  Parámetros:
- owners
- properties
- reviews
- schools

---

# 7. Configuración del Sistema

Archivo `application.properties`:

```
spring.datasource.url=jdbc:h2:file:./db/apartmentpredictordb
spring.datasource.username=oscar
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
app.populate-on-start=true
```

Consola H2:
```
http://localhost:8080/h2-console
```

---

# 8. Flujo Interno de la Aplicación

1. Spring Boot arranca la aplicación.
2. `ApartmentPredictorApplication` ejecuta `@PostConstruct`.
3. Si la base de datos está vacía y `app.populate-on-start=true`:
    - Se ejecuta PopulateDB.populateAll()
4. Se generan Owners, Properties, Schools, Reviewers, Reviews y Contracts.
5. Se asignan relaciones entre entidades.
6. La API REST queda disponible.
7. Los controladores gestionan las peticiones.
8. Los servicios aplican la lógica de negocio.
9. Los repositorios realizan operaciones CRUD.

---

# 9. Utilidades

## 9.1 ApartmentJsonWriter
Permite exportar apartamentos a JSON.

## 9.2 PrintingUtils
Funciones para imprimir listas de entidades en consola.

---

# 10. Estado del Proyecto

- Backend completamente funcional
- Modelo unificado Property
- Herencia JPA con SINGLE_TABLE
- Relaciones complejas entre entidades
- Población automática avanzada
- API REST lista para frontend
- Arquitectura modular y escalable

---
```
