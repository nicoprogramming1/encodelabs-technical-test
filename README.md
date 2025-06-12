# encodelabs-technical-test
This repository is for a Encodelabs technical-test.

### Author: Nicolás W.


Este documento expone el relevamiento del proceso de desarrollo de una aplicación simple
centrada en el CRUD de un PRODUCTO.
Se presentarán las decisiones técnicas más relevantes adoptadas y su justificación.
El documento en su totalidad (excepto el stack tecnológico formateado por IA) fue escrito "a mano" por mí.


# Análisis y diseño de la aplicación

Debido a la simplicidad requerida en el enunciado, no se presentarán modelos de clases de diseño
o diagramas de transición de estados, ni un análisis funcional riguroso.
Se apuesta a lograr una aplicación SIMPLE, que resuelva el enunciado de una manera elegante, basada en buenas prácticas,
utilizando patrones de diseño, principios SOLID, DRY y otros.

De todas maneras, el desarrollo estará guiado según metodologías ágiles (SCRUM), aunque sin hacer
hincapié en el detallismo o ahondar en este aspecto ya que no es requerido y conlleva tiempo.
Las estimaciones y épicas no son rigurosas y las historias de usuario no tienen un comentario, actores ni especificaciones.

El desarrollo tomará sólo un Sprint de 7 días y será gestionado en Jira:

### Jira
https://wnorowsky.atlassian.net/jira/software/projects/ET/boards/38/backlog (deberían tener acceso público)

Al final del docuemnto, junto a la retrospectiva, se presentará el ***BURNDOWN CHART***.


# Decisiones técnicas

Se presenta a continuación las decisiones técnicas adoptadas durante el desarrollo.


### Stack tecnológico

- **Lenguaje:** Java (JDK 21)
- **Framework:** Spring Boot
- **IDE:** IntelliJ
- **Base de Datos:** H2
- **ORM / Persistencia:** Spring Data JPA
- **Mapper:** MapStruct
- **Validaciones:** Jakarta Bean Validation
- **Manejo de errores:** `@ControllerAdvice` | GlobalExceptionHandler + excepciones personalizadas
- **Pruebas:** Postman
- **Arquitectura:** Layered (con ciertos conceptos DDD)
- **Gestión de configuración:** `application.properties` / `application.yml` / `.env`
- **Contenedorización:** Docker
- **Documentación:** Swagger
- **Control de versiones:** Git / Github
- **Gestor de proyecto:** Jira


### Arquitectura

Se adoptará un diseño en capas o ***Layered*** (Controller / Service / Repository) por simplicidad, claridad
y porque permite separar responsabilidades sin "sobre-ingeniería".
De igual manera se tomarán principios de ***DDD*** compatibles con esta arquitectura tales como entities con lógica
y sus propias validaciones de negocio y value objects (como price -> money).
En un entorno más complejo o que fuera a crecer en un futuro, consideraría quizás una arquitectura hexagonal para una
mejor escalabilidad y testeabilidad.
Creo que la arquitectura elegida es ***suficiente***, de lo contrario el costo de desarrollo aumentaría de forma innecesaria.


### Patterns

Realmente hay varios de ellos que por supuesto se implementan de manera implícita, se discrimina solo algunos junto a los
implementandos explícitamente para cubrir una necesidad técnica o por buenas prácticas de desarrollo escalable y de bajo acoplamiento.

- Adapter: de manera implícita, MapStruct actúa como adapter entre los DTOs y el modelo de dominio (en un sistema más complejo
  encapsularía la dependencia detrás de una interfaz para permitir el cambio de estrategia de mapeo pero no lo veo necesario aquí).
- Builder: al recuperar un producto desde DB, construye un producto con todos sus atributos.
- Data Access Object: de manera implícita por ejemplo en JPA Repository
- Data Transfer Object: los DTOs implementados desacoplan la representación externa de la entidad.
- Factory Method: al crear un nuevo producto, con sólo los atributos necesarios provenientes de la request.
- Repository: para acceder a la base de datos evitando acoplamiento con la infrastructura.


