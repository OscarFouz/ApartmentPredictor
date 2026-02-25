# Glosario de Anotaciones y Conceptos Técnicos
Proyecto: ApartmentPredictor  
Tecnologías: Spring Boot 3, Java 21, JPA/Hibernate, H2 Database

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

Ventajas:
- No depende del motor de BD
- Evita colisiones
- Ideal para microservicios o datos generados fuera de la BD

---

## @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
El proyecto actual usa SINGLE_TABLE, no JOINED.

Esto significa:
- Una sola tabla para todas las propiedades
- Una columna discriminadora indica el subtipo
- Mejor rendimiento
- Menos joins
- Más simple de mantener

---

## @DiscriminatorColumn(name = "property_type")
Crea una columna que indica el tipo real de la entidad hija.

Ejemplos:
- APARTMENT
- HOUSE
- DUPLEX
- TOWNHOUSE

---

## @DiscriminatorValue("APARTMENT")
Define el valor que se guardará en `property_type` para esa subclase.

---

## @ManyToOne
Relación muchos-a-uno.

Ejemplos en el proyecto:
- Review → Property
- Review → Reviewer
- PropertyContract → Owner
- PropertyContract → Property

---

## @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
Relación uno-a-muchos.

Ejemplos:
- Property → Reviews
- Property → PropertyContracts
- Owner → Properties

---

## @ManyToMany
Relación muchos-a-muchos.

Ejemplo:
- Property ↔ School

---

## @JoinTable
Define la tabla intermedia en relaciones ManyToMany.

Ejemplo:

```java
@JoinTable(
    name = "property_school",
    joinColumns = @JoinColumn(name = "property_id"),
    inverseJoinColumns = @JoinColumn(name = "school_id")
)
```

---

## @JoinColumn(name = "owner_id")
Define la clave foránea en relaciones ManyToOne.

---

## @Lob
Indica que un campo es un texto largo.

Ejemplo:
- Review.content

---

## @JsonManagedReference
Evita recursión infinita en relaciones bidireccionales.  
Se coloca en el lado "padre".

Ejemplo:
- Property.reviews

---

## @JsonBackReference
Complemento de `@JsonManagedReference`.  
Se coloca en el lado "hijo".

Ejemplo:
- Review.property

---

## @JsonIgnore
Evita que un campo se serialice a JSON.  
Útil para romper ciclos o evitar datos sensibles.

---

## @Transient
Indica que un campo NO debe persistirse en la BD.

Ejemplo recomendado:

```java
@Transient
public String getType() {
    return this.getClass().getSimpleName();
}
```

---

# 2. ANOTACIONES SPRING (SERVICIOS, CONTROLADORES, INYECCIÓN)

## @SpringBootApplication
Anotación principal de Spring Boot. Combina:
- @Configuration
- @EnableAutoConfiguration
- @ComponentScan

---

## @Autowired
Inyección automática de dependencias.

Ejemplo:

```java
@Autowired
private ApartmentRepository apartmentRepository;
```

---

## @Service
Indica que la clase contiene lógica de negocio.

Ejemplos:
- ApartmentService
- OwnerService
- PropertyContractService

---

## @Repository
Indica que la clase es un repositorio de acceso a datos.  
Spring Data JPA genera automáticamente las implementaciones CRUD.

---

## @Component
Componente genérico gestionado por Spring.

Ejemplo:
- PopulateDB
- PrintingUtils

---

## @RestController
Controlador REST que devuelve JSON.

---

## @RequestMapping("/api")
Define la ruta base del controlador.

---

## @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
Definen endpoints HTTP.

- GET → obtener
- POST → crear
- PUT → actualizar
- DELETE → eliminar

---

## @PathVariable
Extrae valores de la URL.

Ejemplo:

```java
@GetMapping("/owners/{id}")
public Owner getOwner(@PathVariable String id)
```

---

## @RequestBody
Convierte automáticamente JSON → objeto Java.

---

## @CrossOrigin(origins = "http://localhost:5173")
Permite peticiones desde el frontend (React/Vite).  
Evita errores CORS.

---

## @Value("${app.populate-on-start}")
Inyecta valores desde application.properties.

---

# 3. CONCEPTOS JPA / HIBERNATE

## Herencia SINGLE_TABLE
Ventajas:
- Más rápida
- Menos joins
- Más simple

Desventajas:
- Muchos campos nulos en la tabla
- Menos normalizada

---

## Relaciones bidireccionales
El proyecto usa varias relaciones bidireccionales:

- Property ↔ Review
- Property ↔ School
- Owner ↔ Property
- Owner ↔ PropertyContract

Se controlan con:
- @JsonManagedReference
- @JsonBackReference

---

## Cascading (CascadeType.ALL)
Propaga operaciones:
- save
- delete
- update

Ejemplo:  
Si se borra un Property → se borran sus Reviews.

---

## FetchType
El proyecto usa principalmente:
- LAZY (por defecto en ManyToMany y ManyToOne)
- EAGER (solo cuando es necesario)

---

## Sesión de Hibernate (Persistence Context)
Hibernate mantiene un "contexto de persistencia" donde almacena temporalmente todas las entidades cargadas durante una transacción.

Conceptos clave:

- La sesión contiene **instancias vivas** de entidades.
- Si se intenta guardar otra instancia con el **mismo ID**, Hibernate lanza:  
  `DuplicateKeyException` o `NonUniqueObjectException`.
- Esto puede ocurrir incluso si la BD está vacía, porque el conflicto sucede **en memoria**, no en la BD.
- Para evitar conflictos, se puede limpiar la sesión con:

```java
entityManager.clear();
```

Esto elimina todas las entidades cargadas en memoria y evita comparaciones entre objetos antiguos y nuevos.

En este proyecto se usa para evitar errores al generar PropertyContracts durante el populate.

---

# 4. CONCEPTOS SPRING BOOT

## CommandLineRunner / @PostConstruct
En la versión actual se usa @PostConstruct para:

- Verificar si la BD está vacía
- Ejecutar PopulateDB.populateAll()

---

## H2 Database (modo archivo)
Configuración:

```
spring.datasource.url=jdbc:h2:file:./db/apartmentpredictordb
```

Ventajas:
- Persistente
- Ligera
- Ideal para desarrollo

---

## application.properties
Controla:
- BD
- Hibernate
- CORS
- Rutas CSV
- populate-on-start

---

# 5. CONCEPTOS DEL DOMINIO (PROYECTO)

## Property
Entidad base de todas las propiedades inmobiliarias.

---

## Apartment / House / Duplex / TownHouse
Subclases de Property.

---

## Owner
Persona propietaria de propiedades y contratos.

---

## Reviewer
Persona que escribe reviews.

---

## Review
Opinión sobre una propiedad.

---

## School
Escuela cercana a una propiedad.

---

## PropertyContract
Contrato entre Owner y Property.

---

## PopulateDB
Generador de datos sintéticos:

- Owners
- Properties
- Schools
- Reviewers
- Reviews
- Contracts

---

# Fin del glosario
