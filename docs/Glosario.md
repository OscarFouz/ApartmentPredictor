# Glosario de Anotaciones y Conceptos Técnicos
Proyecto: ApartmentPredictor
Tecnologías: Spring Boot 3.2, Java 21, JPA/Hibernate, H2 Database, Lombok, OpenAPI

---

# 1. ANOTACIONES JPA (ENTIDADES)

## @Entity
Indica que la clase es una entidad JPA y se mapeará a una tabla en la base de datos.

---

## @Id
Define el campo que actuará como clave primaria.

---

## UUID manual (sin @GeneratedValue)
En este proyecto, la mayoría de entidades usan UUID manual:

```java
this.id = UUID.randomUUID().toString();
```

---

## @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
El proyecto usa SINGLE_TABLE, no JOINED.

Esto significa:
- Una sola tabla para todas las propiedades
- Una columna discriminadora indica el subtipo
- Mejor rendimiento

---

## @DiscriminatorColumn(name = "property_type")
Crea una columna que indica el tipo real de la entidad hija.

---

## @ManyToOne, @OneToMany, @ManyToMany
Relaciones JPA usadas en el proyecto:
- Property → Owner (ManyToOne)
- Property → Reviews (OneToMany)
- Property ↔ School (ManyToMany)
- PropertyContract → Owner/Property (ManyToOne)

---

## @JoinTable
Define la tabla intermedia en relaciones ManyToMany (Property ↔ School).

---

## @Lob
Indica que un campo es un texto largo. Ejemplo: Review.content

---

## @JsonManagedReference / @JsonBackReference
Evita recursión infinita en relaciones bidireccionales.

---

## @JsonIgnore
Evita que un campo se serialice a JSON.

---

# 2. ANOTACIONES SPRING / LOMBOK

## @SpringBootApplication
Anotación principal de Spring Boot.

---

## @Autowired
Inyección automática de dependencias.

---

## @Service, @Repository, @Component
Anotaciones de estereotipos Spring.

---

## @RestController
Controlador REST que devuelve JSON.

---

## @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
Definen endpoints HTTP.

---

## @PathVariable, @RequestBody
Extraen valores de la URL y convierten JSON → objeto Java.

---

## @CrossOrigin
Permite peticiones desde el frontend (React/Vite).

---

## @Data (Lombok)
Genera automáticamente getters, setters, equals, hashCode, toString.

---

## @EqualsAndHashCode(callSuper = true) (Lombok)
Incluye campos heredados en equals/hashCode.

---

# 3. CONCEPTOS JPA / HIBERNATE

## Herencia SINGLE_TABLE
- Más rápida, menos joins
- Campos nulos en la tabla para subclases

## CascadeType.ALL
Propaga operaciones save/delete a entidades relacionadas.

## entityManager.clear()
Limpia el contexto de persistencia para evitar conflictos de objetos duplicados.

---

# 4. SISTEMA DE GRAFOS MANHATTAN

## Haversine
Fórmula para calcular distancia en línea recta entre dos puntos GPS.

## Manhattan Distance
Distancia siguiendo el trazado de calles (como en Manhattan).

## Algoritmo A*
Algoritmo de pathfinding que encuentra el camino más corto en un grafo.

## DistanceCalculator
Clase utilitaria que implementa la fórmula Haversine para todas las distancias del proyecto.

---

# 5. EXCEPCIONES CUSTOM

## PropertyNotFoundException
Lanzada cuando no se encuentra una propiedad por ID.

## UnknownPropertyTypeException
Lanzada cuando el tipo de propiedad no es reconocido.

---

# 6. OPENAPI / SWAGGER

## springdoc-openapi
Biblioteca que genera documentación automática de la API REST.

## @Tag, @Operation, @ApiResponse
Anotaciones para documentar endpoints en Swagger.

## OpenApiConfig
Clase de configuración para personalizar la documentación de la API.

---

# 7. CONCEPTOS DEL DOMINIO

## Property
Entidad base con coordenadas GPS (latitude, longitude) y nearestNodeId.

## School
Escuela con coordenadas GPS y distancia al nodo Manhattan más cercano.

## SchoolDistanceDTO
DTO que contiene escuela + distancia Haversine + distancia Manhattan.

## PopulateDB
Orquestador que usa DataGenerator, DatabaseSeeder y GraphInitializer.

## DataGenerator
Genera datos sintéticos con DataFaker.

## DatabaseSeeder
Guarda datos en BD usando batch operations (saveAll).

## GraphInitializer
Inicializa el grafo Manhattan con 8 nodos (intersecciones reales).

---

# 8. CONFIGURACIÓN

## application.properties
- spring.datasource.url=jdbc:h2:file:./db/apartments
- spring.h2.console.enabled=true
- app.populate-on-start=false

---

# Fin del glosario
