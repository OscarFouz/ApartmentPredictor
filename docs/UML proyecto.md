# UML – Modelo de Entidades (Proyecto ApartmentPredictor)

Este documento representa el modelo de entidades del backend utilizando diagramas ASCII UML.  
Refleja la estructura real del proyecto, incluyendo herencia JPA, relaciones y atributos.

---

# 1. Diagrama de Herencia (Property → Apartment → Subclases)

```
                          ┌──────────────────────────┐
                          │   Property (abstract)     │
                          ├──────────────────────────┤
                          │ - id : String             │
                          │ - area : int              │
                          │ - locationRating : int    │
                          ├──────────────────────────┤
                          │ + getArea()               │
                          │ + getLocationRating()     │
                          │ + setArea(int)            │
                          │ + setLocationRating(int)  │
                          │ + calculatePrice() : dbl  │
                          └───────────┬──────────────┘
                                      │
                ┌─────────────────────┼───────────────────────────┐
                │                     │                           │
                ▼                     ▼                           ▼

      ┌──────────────────────────────┐
      │          Apartment           │
      ├──────────────────────────────┤
      │ - id : String                │
      │ - price : Long               │
      │ - bedrooms : Integer         │
      │ - bathrooms : Integer        │
      │ - stories : Integer          │
      │ - mainroad : String          │
      │ - guestroom : String         │
      │ - basement : String          │
      │ - hotwaterheating : String   │
      │ - airconditioning : String   │
      │ - parking : Integer          │
      │ - prefarea : String          │
      │ - furnishingstatus : String  │
      │ - reviews : List<Review>     │
      ├──────────────────────────────┤
      │ + addReview(Review)          │
      │ + removeReview(Review)       │
      │ + calculatePrice()           │
      └───────────┬──────────────────┘
                  │
                  ▼

      ┌──────────────────────────────┐
      │          TownHouse           │
      ├──────────────────────────────┤
      │ - hasHomeownersAssociation   │
      │ - hoaMonthlyFee : double     │
      ├──────────────────────────────┤
      │ + calculatePrice()           │
      └──────────────────────────────┘


      ┌──────────────────────────────┐
      │            House             │
      ├──────────────────────────────┤
      │ - garageQty : int            │
      │ - roofType : String          │
      │ - garden : String            │
      ├──────────────────────────────┤
      │ + calculatePrice()           │
      └──────────────────────────────┘


      ┌──────────────────────────────┐
      │            Duplex            │
      ├──────────────────────────────┤
      │ - balcony : String           │
      │ - elevator : boolean         │
      │ - hasSeparateUtilities : bool│
      ├──────────────────────────────┤
      │ + calculatePrice()           │
      └──────────────────────────────┘
```

---

# 2. Relación Apartment ↔ Review

Relación real en el proyecto:

- Apartment 1 → * Review
- Review pertenece a un Apartment
- Bidireccional
- Controlada con JsonManagedReference / JsonBackReference

```
      ┌──────────────┐        1        ┌──────────────┐
      │  Apartment    │----------------│    Review     │
      └──────────────┘     0..*        └──────────────┘
             ▲                                  
             │ has many                         
             │                                    
             └───────────────────────────────────────┐
                                                     │
                                                     ▼
```

## Entidad Review (detalle)

```
      ┌──────────────────────────────┐
      │            Review            │
      ├──────────────────────────────┤
      │ - id : String                │
      │ - title : String             │
      │ - content : String           │
      │ - rating : int               │
      │ - reviewDate : LocalDate     │
      │ - apartment : Apartment      │
      ├──────────────────────────────┤
      │ + setApartment(Apartment)    │
      │ + toString()                 │
      └──────────────────────────────┘
```

---

# 3. Entidad Owner (independiente)

Owner no participa en relaciones en este proyecto, pero es una entidad persistente.

```
      ┌──────────────────────────┐
      │          Owner           │
      ├──────────────────────────┤
      │ - id : String            │
      │ - name : String          │
      │ - email : String         │
      │ - age : int              │
      │ - isActive : boolean     │
      │ - isBusiness : boolean   │
      │ - idLegalOwner : String  │
      │ - registrationDate       │
      │ - qtyDaysAsOwner : int   │
      ├──────────────────────────┤
      │ + getters/setters        │
      │ + toString()             │
      └──────────────────────────┘
```

---

# 4. Resumen del Modelo

- **Property** es la clase base abstracta.
- **Apartment** hereda de Property y es la entidad principal.
- **House**, **Duplex** y **TownHouse** heredan de Apartment.
- **Review** se relaciona con Apartment (ManyToOne).
- **Owner** es independiente.
- Se usa herencia JPA con estrategia JOINED.
- Identificadores generados con UUID.

---

# Fin del documento UML
