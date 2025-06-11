# encodelabs-technical-test
This repository is for a Encodelabs technical-test.

### Author: Nicolás W.


Este documento expone el relevamiento del proceso de desarrollo de una aplicación simple
centrada en el CRUD de un PRODUCTO.
Se presentarán las decisiones técnicas más relevantes adoptadas y su justificación.


# Análisis y diseño de la aplicación

Debido a la simplicidad requerida en el enunciado, no se presentarán modelos de clases de diseño
o diagramas de transición de estados, ni un análisis funcional riguroso.
Se apuesta a lograr una aplicación SIMPLE, que resuelva el enunciado de una manera elegante, basada en buenas prácticas,
utilizando patrones de diseño, principios SOLID, DRY y otros.

De todas maneras, el desarrollo estará guiado según metodologías ágiles (SCRUM), aunque sin hacer
hincapié en el detallismo o ahondar en este aspecto ya que no es requerido y conlleva tiempo.
Las estimaciones y/o épicas no son rigurosas, no hay criterios de aceptación ni el comentario propio de una HU.


El desarrollo tomará sólo un Sprint de 7 días y se puede conocer el sprint backlog y su información relevante
en Jira, cuya URL es la siguiente:

### Jira
https://wnorowsky.atlassian.net/jira/software/projects/ET/boards/38/backlog

Al final, a modo de retrospectiva se presentará el burndown chart.


# Decisiones técnicas

Se presenta a continuación las decisiones técnicas concientes adoptadas durante el desarrollo.


### Stack tecnológico

- **Lenguaje:** Java (JDK 21)
- **Framework:** Spring Boot
- **IDE:** IntelliJ IDEA
- **Base de Datos:** H2 (in-memory)
- **ORM / Persistencia:** Spring Data JPA
- **Mapper:** MapStruct (para desacoplar DTOs y entidades de dominio)
- **Validaciones:** Jakarta Bean Validation
- **Manejo de errores:** `@ControllerAdvice` | GlobalExceptionHandler + excepciones personalizadas
- **Pruebas:** Postman (colección con pruebas automáticas incluidas)
- **Arquitectura:** En capas (inspirada en principios de DDD)
- **Gestión de configuración:** `application.properties` / `application.yml` / `.env`
- **Contenedorización:** Docker


### Arquitectura

Se adoptará un diseño en capas o ***Layered*** (Controller / Service / Repository) por simplicidad, claridad
y porque permite separar responsabilidades sin "sobre-ingeniería".
De igual manera se tomarán principios de ***DDD*** compatibles con esta arquitectura tales como entities con lógica
y sus propias validaciones de negocio y value objects (como price -> money).
En un entorno más complejo o que fuera a crecer en un futuro, consideraría quizás una arquitectura hexagonal para una
mejor escalabilidad y testeabilidad.
Creo que la arquitectura elegida es ***suficiente***, de lo contrario el costo de desarrollo aumentaría de forma innecesaria.


### Patterns

Realmente hay varios de ellos que por supuesto se implementan de manera implícita, se presentan solo algunos junto a los
que sí se implementan de manera explícita y para cubrir una necesidad técnica o por buenas prácticas de desarrollo escalable.

- Adapter: de manera implícita, MapStruct actúa como adapter entre los DTOs y el modelo de dominio (en un sistema más complejo
  encapsularía la dependencia detrás de una interfaz para permitir el cambio de estrategia de mapeo pero no lo veo necesario aquí).
- Builder: al recuperar un producto desde DB, construye un producto con todos sus atributos.
- Data Access Object: de manera implícita por ejemplo en JPA Repository
- Data Transfer Object: los DTOs implementados desacoplan la representación externa de la entidad.
- Factory Method: al crear un nuevo producto, con sólo los atributos necesarios provenientes de la request.
- Repository: para acceder a la base de datos evitando acoplamiento con la infrastructura.


