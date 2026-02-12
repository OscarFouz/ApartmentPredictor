# Glosario de Anotaciones y Conceptos Técnicos
Proyecto: ApartmentPredictor  
Tecnologías: Spring Boot 3, Java 21, JPA/Hibernate, H2 Database

---

## @Entity
Indica que la clase es una entidad JPA y se mapeará a una tabla en la base de datos.

---

## @Id
Define el campo que actuará como clave primaria de la entidad.

---

## @GeneratedValue / UUID manual
En este proyecto no se usa `@GeneratedValue`, sino UUID manual:

```java
this.id = UUID.randomUUID().toString();
```

Esto genera un identificador único sin depender del motor de BD.

---

## @Inheritance(strategy = InheritanceType.JOINED)
Define la estrategia de herencia en JPA.  
`JOINED` crea:

- Una tabla padre con atributos comunes.
- Una tabla hija por cada subclase.
- Relaciones 1:1 entre tablas mediante la clave primaria.

Es la estrategia más normalizada y flexible.

---

## @DiscriminatorColumn(name = "property_type")
Crea una columna en la tabla padre (`property`) que indica el tipo real de la entidad hija.

Ejemplo de valores:
- APARTMENT
- HOUSE
- DUPLEX
- TOWNHOUSE

---

## @DiscriminatorValue("APARTMENT")
Define el valor que se almacenará en `property_type` cuando la entidad sea de ese tipo.

Cada subclase tiene su propio valor.

---

## @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
Define una relación uno-a-muchos entre Apartment y Review.

- **mappedBy**: indica que la FK está en la entidad Review.
- **cascade = ALL**: si se borra un Apartment, se borran sus Reviews.
- **fetch = EAGER**: las reviews se cargan automáticamente al obtener un Apartment.

---

## @ManyToOne
Define la relación inversa: muchas reviews pertenecen a un solo apartamento.

---

## @JoinColumn(name = "apartment_fk")
Indica el nombre de la columna que actuará como clave foránea en la tabla `review`.

---

## @JsonManagedReference
Evita recursión infinita en relaciones bidireccionales al serializar a JSON.  
Se coloca en el lado "padre" (Apartment).

---

## @JsonBackReference
Complemento de `@JsonManagedReference`.  
Se coloca en el lado "hijo" (Review) para evitar ciclos.

---

## @SpringBootApplication
Anotación principal de Spring Boot. Combina:

- @Configuration
- @EnableAutoConfiguration
- @ComponentScan

Permite que Spring detecte automáticamente controladores, servicios y repositorios.

---

## @Autowired
Inyección automática de dependencias gestionadas por Spring.

Ejemplo:

```java
@Autowired
private ApartmentRepository apartmentRepository;
```

---

## @Service
Indica que la clase contiene lógica de negocio.  
Spring la registra como un componente del contenedor.

---

## @Repository
Indica que la clase es un repositorio de acceso a datos.  
Spring Data JPA genera automáticamente las implementaciones CRUD.

---

## @RestController
Combina:

- @Controller
- @ResponseBody

Indica que la clase expone endpoints REST y devuelve JSON.

---

## @RequestMapping("/api")
Define la ruta base para un controlador.

---

## @GetMapping, @PostMapping, @PutMapping, @DeleteMapping
Definen endpoints HTTP específicos:

- GET → obtener datos
- POST → crear
- PUT → actualizar
- DELETE → eliminar

---

## @PathVariable
Extrae valores de la URL.

Ejemplo:

```java
@GetMapping("/apartments/{id}")
public Apartment getById(@PathVariable String id)
```

---

## @RequestBody
Indica que el cuerpo del request debe convertirse automáticamente desde JSON a un objeto Java.

---

## @CrossOrigin(origins = "http://localhost:5173")
Permite peticiones desde el frontend (React).  
Evita errores CORS.

---

## @Value("${app.csv.path}")
Inyecta valores desde `application.properties`.

Ejemplo:

```
app.csv.path=db/Housing.csv
```

Uso:

```java
@Value("${app.csv.path}")
private String csvPath;
```

---

## @Component
Indica que la clase es un componente genérico gestionado por Spring.  
Se usa en utilidades como `PrintingUtils`.

---

## @Transient
Indica que un campo no debe persistirse en la base de datos.

Ejemplo recomendado:

```java
@Transient
public String getPropertyType() {
    return this.getClass().getSimpleName();
}
```

---

## @DiscriminatorValue en subclases
Cada subclase define su tipo:

```java
@DiscriminatorValue("HOUSE")
public class House extends Apartment { ... }
```

Esto permite a Hibernate reconstruir el tipo real al leer desde la BD.

---

## @JoinColumn(name = "apartment_fk")
Define la clave foránea en Review que apunta a Apartment.

---

## @CommandLineRunner
Permite ejecutar código automáticamente al iniciar la aplicación.

En este proyecto se usa para:

- Cargar apartamentos desde CSV
- Cargar reviews desde CSV

---

## @JsonIgnore (no usado, pero útil)
Evita que un campo se serialice a JSON.  
Alternativa a `@JsonBackReference`.

---

## @Entity + Herencia JOINED
En este proyecto, todas las entidades hijas (`House`, `Duplex`, `TownHouse`) heredan de `Apartment`, que a su vez hereda de `Property`.

Esto crea una jerarquía de tablas:

```
property
apartment
house
duplex
townhouse
```

---

# Fin del glosario
