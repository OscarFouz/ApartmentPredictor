# ApartmentPredictor
Sistema de gestión y análisis de apartamentos con soporte para herencia JPA, reviews y carga automática desde CSV.

---

# Backend — Spring Boot + H2 + JPA (JOINED)

Este backend implementa un sistema completo para gestionar apartamentos y sus reseñas, con soporte para:

- Persistencia en H2 Database (modo archivo)
- API REST con Spring Boot
- Herencia JPA con estrategia JOINED
- Entidades polimórficas: Apartment, House, Duplex, TownHouse
- Gestión de reviews asociadas a apartamentos
- Carga inicial de datos desde CSV
- Serialización JSON con Jackson
- Controladores REST desacoplados por dominio
- Exportación de datos a JSON

---

# Arquitectura del Proyecto

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

# Modelo de Datos

## Herencia JPA — Clase base Property

El proyecto utiliza herencia con estrategia JOINED, lo que genera:

- Una tabla base `property`
- Una columna discriminadora `property_type`
- Una tabla por cada subclase (`apartment`, `house`, `duplex`, `townhouse`)

Para exponer el tipo al frontend se recomienda:

```java
@Transient
public String getPropertyType() {
    return this.getClass().getSimpleName();
}
```

---

# Entidad Apartment

- Entidad principal del sistema.
- Contiene atributos comunes (price, bedrooms, bathrooms, etc.)
- Relación OneToMany con Review:

```java
@OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
@JsonManagedReference
```

- Métodos helper: addReview(), removeReview()
- Constructor con UUID automático

---

# Subclases de Apartment

## House
Atributos adicionales:
- garageQty
- roofType
- garden

## Duplex
Atributos adicionales:
- balcony
- elevator
- hasSeparateUtilities

## TownHouse
Atributos adicionales:
- hasHomeownersAssociation
- hoaMonthlyFee

Cada subclase redefine calculatePrice().

---

# Entidad Review

- Relación ManyToOne con Apartment:

```java
@ManyToOne
@JoinColumn(name = "apartment_fk")
@JsonBackReference
```

- Campos:
    - title
    - content
    - rating
    - reviewDate

- UUID automático

---

# Repositorios

- ApartmentRepository → CrudRepository<Apartment, String>
- ReviewRepository → CrudRepository<Review, String>

---

# Servicios

## ApartmentService
- CRUD completo
- Actualización de reviews
- Búsqueda por ID

## LoadInitialDataService
- Carga apartamentos desde CSV
- Genera tipos aleatorios (Apartment, House, Duplex, TownHouse)

## LoadReviewDataService
- Carga reviews desde CSV
- Asigna cada review a un apartamento aleatorio

---

# Controladores REST

## ApartmentController
- GET /api/apartments
- GET /api/apartments/{id}
- POST /api/apartments
- PUT /api/apartments/{id}
- DELETE /api/apartments/{id}
- GET /api/apartments/export → genera apartments.json

## ReviewController
- GET /api/apartments/{id}/reviews
- POST /api/apartments/{id}/reviews
- DELETE /api/reviews/{reviewId}

---

# Configuración H2 (application.properties)

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

# Ejecución

```
mvn spring-boot:run
```

---

# Exportación de Datos

```
GET /api/apartments/export
```

Genera un archivo apartments.json con todos los apartamentos.

---

# Estado del Proyecto

- Backend funcional
- Herencia JPA estable
- Relaciones bidireccionales controladas
- Carga automática desde CSV
- API lista para integrarse con React

