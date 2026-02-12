# MANUAL TÉCNICO
Proyecto: ApartmentPredictor  
Tecnologías: Spring Boot 3, Java 21, JPA/Hibernate, H2 Database, Maven

---

## 1. Introducción

ApartmentPredictor es un backend desarrollado con Spring Boot que permite gestionar apartamentos, sus características y las reseñas asociadas.  
El sistema implementa un modelo de herencia JPA, carga automática de datos desde archivos CSV, persistencia en H2 Database y una API REST completa para interactuar con un frontend (por ejemplo, React).

Este manual técnico describe la arquitectura interna, el modelo de datos, la estructura del código, la configuración del entorno y el funcionamiento de los servicios principales.

---

## 2. Arquitectura General del Proyecto

El proyecto sigue una arquitectura modular basada en controladores, servicios, repositorios y entidades JPA.

```
src/main/java/com/example/apartment_predictor
│
├── controller
│   ├── ApartmentController.java
│   └── ReviewController.java
│
├── model
│   ├── Property.java
│   ├── Apartment.java
│   ├── House.java
│   ├── Duplex.java
│   ├── TownHouse.java
│   ├── Review.java
│   └── Owner.java
│
├── repository
│   ├── ApartmentRepository.java
│   └── ReviewRepository.java
│
├── service
│   ├── ApartmentService.java
│   ├── LoadInitialDataService.java
│   ├── LoadReviewDataService.java
│   └── ReviewService.java
│
└── utils
    ├── ApartmentJsonWriter.java
    └── PrintingUtils.java
```

---

## 3. Modelo de Datos

### 3.1 Herencia JPA

El sistema utiliza herencia con estrategia JOINED:

- Una tabla base `property`
- Una columna discriminadora `property_type`
- Una tabla por cada subclase:
    - apartment
    - house
    - duplex
    - townhouse

Ejemplo de discriminador:

```java
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "property_type")
```

### 3.2 Entidad Property (clase base)

Contiene atributos comunes:

- id (UUID)
- area
- locationRating

### 3.3 Entidad Apartment

Extiende Property y añade:

- price
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

Relación con Review:

```java
@OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
@JsonManagedReference
```

### 3.4 Subclases de Apartment

#### House
- garageQty
- roofType
- garden

#### Duplex
- balcony
- elevator
- hasSeparateUtilities

#### TownHouse
- hasHomeownersAssociation
- hoaMonthlyFee

Cada subclase redefine `calculatePrice()`.

### 3.5 Entidad Review

Relación ManyToOne:

```java
@ManyToOne
@JoinColumn(name = "apartment_fk")
@JsonBackReference
```

Campos:

- title
- content
- rating
- reviewDate

---

## 4. Repositorios

El proyecto utiliza Spring Data JPA.

### ApartmentRepository

```java
public interface ApartmentRepository extends CrudRepository<Apartment, String> {}
```

### ReviewRepository

```java
public interface ReviewRepository extends CrudRepository<Review, String> {}
```

---

## 5. Servicios

### 5.1 ApartmentService

Funciones principales:

- Crear apartamentos
- Actualizar apartamentos
- Eliminar apartamentos
- Buscar por ID
- Gestionar reviews asociadas

### 5.2 LoadInitialDataService

Carga apartamentos desde un archivo CSV:

- Genera un tipo aleatorio (Apartment, House, Duplex, TownHouse)
- Asigna valores comunes
- Asigna valores específicos según el tipo

### 5.3 LoadReviewDataService

Carga reviews desde CSV:

- Asigna cada review a un apartamento aleatorio
- Guarda en la base de datos

### 5.4 ReviewService

Servicio auxiliar para gestionar reviews.

---

## 6. Controladores REST

### 6.1 ApartmentController

Endpoints:

- GET /api/apartments
- GET /api/apartments/{id}
- POST /api/apartments
- PUT /api/apartments/{id}
- DELETE /api/apartments/{id}
- GET /api/apartments/export

### 6.2 ReviewController

Endpoints:

- GET /api/apartments/{id}/reviews
- POST /api/apartments/{id}/reviews
- DELETE /api/reviews/{reviewId}

---

## 7. Configuración del Sistema

Archivo `application.properties`:

```
spring.datasource.url=jdbc:h2:file:./db/apartmentpredictordb
spring.datasource.username=oscar
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
app.csv.path=db/Housing.csv
app.reviews.csv.path=db/Reviews.csv
```

Consola H2:

```
http://localhost:8080/h2-console
```

---

## 8. Ejecución del Proyecto

### 8.1 Compilar

```
mvn clean package
```

### 8.2 Ejecutar

```
mvn spring-boot:run
```

### 8.3 Exportar datos a JSON

```
GET /api/apartments/export
```

Genera el archivo:

```
apartments.json
```

---

## 9. Flujo Interno de la Aplicación

1. Al iniciar, Spring Boot ejecuta `CommandLineRunner`.
2. Se verifica si la base de datos está vacía.
3. Si no hay apartamentos:
    - Se cargan desde CSV.
4. Si no hay reviews:
    - Se cargan desde CSV.
5. El servidor queda disponible en el puerto 8080.
6. Los controladores exponen la API REST.
7. Los servicios gestionan la lógica de negocio.
8. Los repositorios realizan operaciones CRUD.
9. Jackson serializa las entidades para enviarlas al frontend.

---

## 10. Exportación de Datos

La clase `ApartmentJsonWriter` permite exportar todos los apartamentos a un archivo JSON:

```java
mapper.writerWithDefaultPrettyPrinter()
      .writeValue(new File("apartments.json"), apartments);
```

---

## 11. Estado del Proyecto

- Backend completamente funcional
- Herencia JPA estable
- Relaciones bidireccionales controladas con Jackson
- Carga automática desde CSV
- API REST lista para integrarse con frontend
- Exportación de datos operativa

---
