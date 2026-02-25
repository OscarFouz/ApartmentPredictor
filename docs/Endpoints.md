```md
# Endpoints REST  
Proyecto: ApartmentPredictor  
Arquitectura: Spring Boot 3, Controladores REST, JSON

---

# Introducción

Este archivo describe todos los endpoints REST expuestos por el backend.  
Cada sección corresponde a un controlador del proyecto.

---

# 1. Endpoints de Apartments

Base: `/api/apartments`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/apartments | Obtiene todos los apartamentos |
| GET | /api/apartments/{id} | Obtiene un apartamento por ID |
| POST | /api/apartments | Crea un nuevo apartamento |
| PUT | /api/apartments/{id} | Actualiza un apartamento |
| DELETE | /api/apartments/{id} | Elimina un apartamento |
| PUT | /api/apartments/{id}/assign-schools | Asigna escuelas a un apartamento |

---

# 2. Endpoints de Houses

Base: `/api/houses`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/houses | Lista todas las casas |
| GET | /api/houses/{id} | Obtiene una casa por ID |
| POST | /api/houses | Crea una casa |
| PUT | /api/houses/{id} | Actualiza una casa |
| DELETE | /api/houses/{id} | Elimina una casa |

---

# 3. Endpoints de Duplexes

Base: `/api/duplexes`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/duplexes | Lista todos los dúplex |
| GET | /api/duplexes/{id} | Obtiene un dúplex por ID |
| POST | /api/duplexes | Crea un dúplex |
| PUT | /api/duplexes/{id} | Actualiza un dúplex |
| DELETE | /api/duplexes/{id} | Elimina un dúplex |

---

# 4. Endpoints de TownHouses

Base: `/api/townhouses`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/townhouses | Lista todos los townhouses |
| GET | /api/townhouses/{id} | Obtiene un townhouse por ID |
| POST | /api/townhouses | Crea un townhouse |
| PUT | /api/townhouses/{id} | Actualiza un townhouse |
| DELETE | /api/townhouses/{id} | Elimina un townhouse |

---

# 5. Endpoints de Owners

Base: `/api/owners`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/owners | Lista todos los propietarios |
| GET | /api/owners/{id} | Obtiene un propietario por ID |
| POST | /api/owners | Crea un propietario |
| PUT | /api/owners/{id} | Actualiza un propietario |
| DELETE | /api/owners/{id} | Elimina un propietario |
| GET | /api/owners/{id}/houses | Obtiene casas del propietario |
| GET | /api/owners/{id}/duplexes | Obtiene dúplex del propietario |
| GET | /api/owners/{id}/townhouses | Obtiene townhouses del propietario |

---

# 6. Endpoints de Reviewers

Base: `/api/reviewers`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/reviewers | Lista reviewers |
| GET | /api/reviewers/{id} | Obtiene un reviewer |
| POST | /api/reviewers | Crea un reviewer |
| PUT | /api/reviewers/{id} | Actualiza un reviewer |
| DELETE | /api/reviewers/{id} | Elimina un reviewer |
| GET | /api/reviewers/{id}/reviews | Obtiene reviews del reviewer |

---

# 7. Endpoints de Reviews

Base: `/api/reviews`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/reviews/property/{id} | Obtiene reviews de una propiedad |
| POST | /api/reviews/property/{id} | Crea una review para una propiedad |
| DELETE | /api/reviews/{reviewId} | Elimina una review |

---

# 8. Endpoints de Property Contracts

Base: `/api/contracts`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/contracts | Lista todos los contratos |
| GET | /api/contracts/{id} | Obtiene un contrato por ID |
| POST | /api/contracts | Crea un contrato |
| PUT | /api/contracts/{id}/close | Cierra un contrato |
| DELETE | /api/contracts/{id} | Elimina un contrato |

---

# 9. Endpoint de PopulateDB

Base: `/api/populate`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/populate | Población automática de datos |

Parámetros opcionales:
- owners  
- properties  
- reviews  
- schools  

Ejemplo:
```
/api/populate?owners=20&properties=50&reviews=100&schools=10
```

---

# Fin del glosario de endpoints
```
