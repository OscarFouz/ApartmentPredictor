# UML – Modelo de Entidades (Proyecto ApartmentPredictor)

Este documento representa el modelo de entidades del backend.
Refleja la estructura REAL del proyecto actual, incluyendo:

- Herencia JPA con SINGLE_TABLE
- Relaciones entre entidades
- Atributos principales (generados por Lombok @Data)
- Sistema de grafos Manhattan

---

# 1. Diagrama de Herencia (Property → Apartment / House / Duplex / TownHouse)

```
                            ┌──────────────────────────────────────┐
                            │        Property (abstract)           │
                            ├──────────────────────────────────────┤
                            │ - id : String                       │
                            │ - address : String                  │
                            │ - price : Integer                  │
                            │ - latitude : double                 │
                            │ - longitude : double                │
                            │ - nearestNodeId : Integer           │
                            │ - owner : Owner                     │
                            │ - nearbySchools : List<School>      │
                            │ - propertyContracts : List<PC>       │
                            │ - reviews : List<Review>            │
                            ├──────────────────────────────────────┤
                            │ + getters/setters (@Data Lombok)    │
                            └───────────────────┬──────────────────┘
                                                │
         ┌──────────────────────────────────────┼─────────────────────────────────────┐
         │                                      │                                     │
         ▼                                      ▼                                     ▼

┌──────────────────────────────────────┐   ┌──────────────────────────────────────┐
│              Apartment                 │   │                House                 │
├──────────────────────────────────────┤   ├──────────────────────────────────────┤
│ - name : String                      │   │ - name : String                      │
│ - area : Integer                     │   └──────────────────────────────────────┘
│ - bedrooms : Integer                 │
│ - bathrooms : Integer                │
│ - stories : Integer                  │
│ - mainroad : String                  │
│ - guestroom : String                 │
│ - basement : String                  │
│ - hotwaterheating : String           │
│ - airconditioning : String           │
│ - parking : Integer                  │
│ - prefarea : String                  │
│ - furnishingstatus : String          │
└──────────────────────────────────────┘

┌──────────────────────────────────────┐   ┌──────────────────────────────────────┐
│                Duplex                 │   │              TownHouse               │
├──────────────────────────────────────┤   ├──────────────────────────────────────┤
│ - name : String                      │   │ - name : String                      │
└──────────────────────────────────────┘   └──────────────────────────────────────┘
```

---

# 2. Person (clase base abstracta)

```
┌──────────────────────────────────────┐
│               Person                  │
├──────────────────────────────────────┤
│ - id : String                        │
│ - fullName : String                  │
│ - birthDate : LocalDate              │
│ - phone : String                     │
│ - email : String                     │
│ - password : String                  │
│ - isActive : boolean                 │
│ - role : String                      │
├──────────────────────────────────────┤
│ + getters/setters (@Data Lombok)    │
└───────────────────┬──────────────────┘
                    │
        ┌───────────┴───────────┐
        ▼                       ▼
┌──────────────────────┐   ┌──────────────────────┐
│       Owner          │   │      Reviewer        │
├──────────────────────┤   ├──────────────────────┤
│ - isBusiness: boolean│   │ - reputation: int    │
│ - idLegalOwner: Str │   │ - isBusiness: boolean │
│ - registrationDate  │   │ - xAccount: String   │
│ - qtyDaysAsOwner   │   │ - webURL: String     │
│ - contracts: List<> │   │ - qtyReviews: int   │
│ - apartments: List<>│   │ - reviews: List<>   │
│ - houses: List<>    │   └──────────────────────┘
│ - duplexes: List<>  │
│ - townHouses: List<>│
└──────────────────────┘
```

---

# 3. Review

```
┌──────────────────────────────────────┐
│                Review                │
├──────────────────────────────────────┤
│ - id : String                        │
│ - title : String                     │
│ - content : String (Lob)            │
│ - rating : int                       │
│ - reviewDate : LocalDate           │
│ - property : Property                │
│ - reviewer : Reviewer                │
└──────────────────────────────────────┘
```

---

# 4. School

```
┌──────────────────────────────────────┐
│                School                │
├──────────────────────────────────────┤
│ - id : String                        │
│ - name : String                      │
│ - address : String                   │
│ - type : String                      │
│ - educationLevel : String            │
│ - location : String                  │
│ - rating : int                       │
│ - isPublic : boolean                │
│ - latitude : double                  │
│ - longitude : double                 │
│ - nearestNodeId : Integer           │
└──────────────────────────────────────┘
```

---

# 5. PropertyContract

```
┌──────────────────────────────────────┐
│          PropertyContract            │
├──────────────────────────────────────┤
│ - id : String                        │
│ - contractName : String             │
│ - contractDetails : String           │
│ - agreedPrice : double              │
│ - startDate : LocalDate             │
│ - endDate : LocalDate               │
│ - active : boolean                  │
│ - owner : Owner                      │
│ - property : Property               │
└──────────────────────────────────────┘
```

---

# 6. Sistema de Grafos Manhattan

```
┌──────────────────────────────────────┐
│                 Graph                │
├──────────────────────────────────────┤
│ - nodes : Map<Integer, Node>        │
│ - adj : Map<Integer, List<Edge>>    │
├──────────────────────────────────────┤
│ + addNode()                         │
│ + addEdge()                         │
└──────────────────────────────────────┘

┌──────────────────────────────────────┐
│                 Node                 │
├──────────────────────────────────────┤
│ - id : int                          │
│ - lat : double                       │
│ - lon : double                       │
└──────────────────────────────────────┘

┌──────────────────────────────────────┐
│                 Edge                 │
├──────────────────────────────────────┤
│ - to : int                          │
│ - cost : double                      │
└──────────────────────────────────────┘

┌──────────────────────────────────────┐
│                AStar                 │
├──────────────────────────────────────┤
│ + shortestPath(): Optional<Double>    │
└──────────────────────────────────────┘

┌──────────────────────────────────────┐
│       ManhattanGraphService          │
├──────────────────────────────────────┤
│ - graph : Graph                      │
│ - aStar : AStar                     │
├──────────────────────────────────────┤
│ + distance(): Optional<Double>       │
│ + calculateDistance(): Optional<Double>│
│ + findClosestNode(): int            │
└──────────────────────────────────────┘
```

---

# 7. DTO - SchoolDistanceDTO

```
┌──────────────────────────────────────┐
│          SchoolDistanceDTO           │
├──────────────────────────────────────┤
│ - school : School                    │
│ - haversineMeters : double          │
│ - manhattanMeters : double          │
└──────────────────────────────────────┘
```

---

# 8. Resumen del Modelo

- **Property** es la clase base abstracta con coordenadas GPS.
- **Apartment, House, Duplex, TownHouse** heredan de Property.
- **Person** es la clase base para Owner y Reviewer.
- **Owner** administra propiedades y contratos.
- **Reviewer** escribe reviews.
- **Review** pertenece a Property y Reviewer.
- **School** se relaciona con Property (ManyToMany) con coordenadas GPS.
- **PropertyContract** une Owner ↔ Property.
- **Grafos**: Graph, Node, Edge, AStar para distancias Manhattan.
- **DTO**: SchoolDistanceDTO para transporte de distancias.
- Herencia JPA: **SINGLE_TABLE**.
- Lombok @Data para getters/setters automáticos.

---

# Fin del documento UML
