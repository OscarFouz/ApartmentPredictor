```md
# UML – Modelo de Entidades (Proyecto ApartmentPredictor – versión actualizada)

Este documento representa el modelo de entidades del backend utilizando diagramas ASCII UML.  
Refleja la estructura REAL del proyecto actual, incluyendo:

- Herencia JPA con SINGLE_TABLE  
- Relaciones entre entidades  
- Atributos principales  
- Estructura de Property y sus subclases  
- Reviewer, Review, Owner, School, PropertyContract  

---

# 1. Diagrama de Herencia (Property → Apartment / House / Duplex / TownHouse)

```
                           ┌──────────────────────────────────────┐
                           │        Property (abstract)           │
                           ├──────────────────────────────────────┤
                           │ - id : String                        │
                           │ - address : String                   │
                           │ - owner : Owner                      │
                           │ - nearbySchools : List<School>       │
                           │ - propertyContracts : List<PC>       │
                           │ - reviews : List<Review>             │
                           ├──────────────────────────────────────┤
                           │ + getters/setters                    │
                           └───────────────────┬──────────────────┘
                                               │
         ┌─────────────────────────────────────┼─────────────────────────────────────┐
         │                                     │                                     │
         ▼                                     ▼                                     ▼

┌──────────────────────────────────────┐   ┌──────────────────────────────────────┐
│              Apartment               │   │                House                 │
├──────────────────────────────────────┤   ├──────────────────────────────────────┤
│ - name : String                      │   │ - name : String                      │
│ - price : Integer                    │   │                                      │
│ - area : Integer                     │   │                                      │
│ - bedrooms : Integer                 │   │                                      │
│ - bathrooms : Integer                │   │                                      │
│ - stories : Integer                  │   │                                      │
│ - mainroad : String                  │   │                                      │
│ - guestroom : String                 │   │                                      │
│ - basement : String                  │   │                                      │
│ - hotwaterheating : String           │   │                                      │
│ - airconditioning : String           │   │                                      │
│ - parking : Integer                  │   │                                      │
│ - prefarea : String                  │   │                                      │
│ - furnishingstatus : String          │   │                                      │
├──────────────────────────────────────┤   ├──────────────────────────────────────┤
│ + getters/setters                    │   │ + getters/setters                    │
└───────────────────┬──────────────────┘   └───────────────────┬──────────────────┘
│                                          │
▼                                          ▼

┌──────────────────────────────────────┐   ┌──────────────────────────────────────┐
│                Duplex                │   │              TownHouse               │
├──────────────────────────────────────┤   ├──────────────────────────────────────┤
│ - name : String                      │   │ - name : String                      │
├──────────────────────────────────────┤   ├──────────────────────────────────────┤
│ + getters/setters                    │   │ + getters/setters                    │
└──────────────────────────────────────┘   └──────────────────────────────────────┘
```

---

# 2. Relación Property ↔ Review

```
┌──────────────┐        1        ┌──────────────┐
│   Property    │----------------│    Review     │
└──────────────┘      0..*       └──────────────┘
```

### Review (detalle)

```
┌──────────────────────────────────────┐
│                Review                │
├──────────────────────────────────────┤
│ - id : String                        │
│ - title : String                     │
│ - content : String                   │
│ - rating : int                       │
│ - reviewDate : LocalDate             │
│ - property : Property                │
│ - reviewer : Reviewer                │
├──────────────────────────────────────┤
│ + getters/setters                    │
└──────────────────────────────────────┘
```

---

# 3. Reviewer (hereda de Person)

```
┌──────────────────────────────────────┐
│               Person                 │
├──────────────────────────────────────┤
│ - id : String                        │
│ - fullName : String                  │
│ - birthDate : LocalDate              │
│ - phone : String                     │
│ - email : String                     │
│ - password : String                  │
│ - isActive : boolean                 │
└───────────────────┬──────────────────┘
│
▼
┌──────────────────────────────────────┐
│               Reviewer               │
├──────────────────────────────────────┤
│ - reputation : int                   │
│ - isBusiness : boolean               │
│ - xAccount : String                  │
│ - webURL : String                    │
│ - qtyReviews : int                   │
├──────────────────────────────────────┤
│ + getters/setters                    │
└──────────────────────────────────────┘
```

---

# 4. Owner (hereda de Person)

```
┌──────────────────────────────────────┐
│                Owner                 │
├──────────────────────────────────────┤
│ - isBusiness : boolean               │
│ - idLegalOwner : String              │
│ - registrationDate : LocalDate       │
│ - qtyDaysAsOwner : int               │
│ - contracts : List<PropertyContract> │
│ - apartments : List<Apartment>       │
│ - houses : List<House>               │
│ - duplexes : List<Duplex>            │
│ - townHouses : List<TownHouse>       │
├──────────────────────────────────────┤
│ + getters/setters                    │
└──────────────────────────────────────┘
```

---

# 5. PropertyContract

```
┌──────────────────────────────────────┐
│          PropertyContract            │
├──────────────────────────────────────┤
│ - id : String                        │
│ - contractName : String              │
│ - contractDetails : String           │
│ - agreedPrice : double               │
│ - startDate : LocalDate              │
│ - endDate : LocalDate                │
│ - active : boolean                   │
│ - owner : Owner                      │
│ - property : Property                │
├──────────────────────────────────────┤
│ + getters/setters                    │
└──────────────────────────────────────┘
```

---

# 6. School (ManyToMany con Property)

```
┌──────────────┐      *      ┌──────────────┐
│   Property    │-------------│    School     │
└──────────────┘      *      └──────────────┘
```

### School (detalle)

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
│ - isPublic : boolean                 │
├──────────────────────────────────────┤
│ + getters/setters                    │
└──────────────────────────────────────┘
```

---

# 7. Resumen del Modelo

- **Property** es la clase base abstracta.  
- **Apartment, House, Duplex, TownHouse** heredan de Property.  
- **Owner** administra propiedades y contratos.  
- **Reviewer** escribe reviews.  
- **Review** pertenece a Property y Reviewer.  
- **School** se relaciona con Property (ManyToMany).  
- **PropertyContract** une Owner ↔ Property.  
- Herencia JPA: **SINGLE_TABLE**.  
- IDs generados con UUID.  

---

# Fin del documento UML
```
