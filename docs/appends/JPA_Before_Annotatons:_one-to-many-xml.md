# Before annotations: one to many .xml

> Antes de que las <mark>anotaciones</mark> fueran comunes, la relación `Apartment–Review` se definía en `ORM XML` (Hibernate `*.hbm.xml` o JPA `orm.xml`), y Spring solo conectaba la `ORM layer` (`SessionFactory`/`EntityManagerFactory`, **transacciones**) mediante su propio XML.  
> 
> El <mark>mapeo</mark> en sí no vivía dentro del XML de Spring.

## Hibernate mapping XML example

En el Hibernate clásico (estilo pre‑JPA), se usaba algo como lo siguiente:

- `Apartment.hbm.xml` definía la tabla y la colección one‑to‑many.  
- `Review.hbm.xml` definía la tabla y la relación many‑to‑one hacia `Apartment`.

Conceptualmente (simplificado):

**Apartment.hbm.xml**

```xml
<class name="com.example.Apartment" table="apartment">
    <id name="id" column="id" type="string">
        <generator class="assigned"/>
    </id>

    <!-- other primitive fields -->

    <set name="reviews" inverse="true" cascade="all">
        <key column="apartment_fk"/>
        <one-to-many class="com.example.Review"/>
    </set>
</class>
```

**Review.hbm.xml**

```xml
<class name="com.example.Review" table="review">
    <id name="id" column="id" type="string">
        <generator class="assigned"/>
    </id>

    <!-- title, content, rating, reviewDate, ... -->

    <many-to-one name="apartment"
                 class="com.example.Apartment"
                 column="apartment_fk"
                 not-null="true"/>
</class>
```

> Esto produce exactamente el mismo schema que tu ejemplo con anotaciones: una foreign key `review.apartment_fk` apuntando a `apartment.id`.

## JPA orm.xml variant

Si en lugar de usar Hibernate XML puro utilizabas JPA con XML mappings, la idea era similar: definir las entities en Java como simples <mark>POJOs</mark>, y luego describir el table/relationship mapping en `META-INF/orm.xml`.

Allí se configuraba:

- Una `<entity class="...Apartment">`:
  - con un `<one-to-many>` que referencia a `Review`.
- Una `<entity class="...Review">`:
  - con un `<many-to-one>` y `<join-column name="apartment_fk"/>`.

De nuevo, el mismo modelo relacional, solo que expresado en JPA XML en lugar de annotations.

## Como lo vió Spring

Spring antes de `Boot`:

- Definía `DataSource`, `LocalSessionFactoryBean` (para Hibernate) o `LocalContainerEntityManagerFactoryBean` (para JPA) en Spring XML, apuntando a `Apartment.hbm.xml` / `Review.hbm.xml` o `persistence.xml` / `orm.xml`.
- Definía un **transaction manager bean y DAOs/services** que inyectaban `SessionFactory` o `EntityManager`.

Así que para “crear la relación sin anotaciones” entre `Apartment`–`Review`:

- <mark>Entities</mark>: clases Java simples con fields y getters/setters, sin `@Entity`, `@OneToMany`, etc.
- <mark>Relationship</mark>: definida en Hibernate `*.hbm.xml` o JPA `orm.xml`.
- Spring: solo responsable de conectar la configuración del ORM y las transactions, no de modelar la asociación en sí.

## References:

1. [13 & Object Relational Mapping (ORM) Data Access](https://docs.spring.io/spring-framework/docs/3.0.1.RELEASE/reference/html/orm.html)
2. [40 & XML Schema-based configuration](https://docs.spring.io/spring-framework/docs/4.2.x/spring-framework-reference/html/xsd-configuration.html)
3. [A Beginner&#039;s Guide to JPA&#039;s persistence.xml](https://thorben-janssen.com/jpa-persistence-xml/)
4. [Mapping JPA to XML](https://docs.oracle.com/middleware/1213/toplink/solutions/jpatoxml.htm)
5. [Using Hibernate ORM and Jakarta Persistence - Quarkus](https://quarkus.io/guides/hibernate-orm)
6. [Working with Relationships in Spring Data REST | Baeldung](https://www.baeldung.com/spring-data-rest-relationships)
7. [Hibernate ORM 5.4.33.Final User Guide](https://docs.hibernate.org/orm/5.4/userguide/html_single)
8. [Define @Type in orm.xml (Spring Data / JPA 2.1 /Hibernate 5.3.7/Postgresql)? - Stack Overflow](https://stackoverflow.com/questions/55046340/define-type-in-orm-xml-spring-data-jpa-2-1-hibernate-5-3-7-postgresql)
9. [The Grails Framework 2.1.1](https://grails.apache.org/docs/2.1.1/guide/single.html)
10. https://docs.spring.io/spring-integration/docs/2.1.0.RELEASE/reference/pdf/spring-integration-reference.pdf
