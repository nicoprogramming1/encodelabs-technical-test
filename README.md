# encodelabs-technical-test

This repository is for a Encodelabs technical-test.

### Author: Nicolás W.

Este documento detalla el análisis, desarrollo y las decisiones técnicas del presente proceso de selección.
El documento en su totalidad (excepto el formato del stack tecnológico por IA) fue escrito a mano.


# RUN EncodelabsApplication

### Levantar la aplicación con Docker

Este proyecto usa Docker / Docker Compose para construir y ejecutar la aplicación Spring Boot.

### Requisitos

- Docker instalado y corriendo (Docker Compose)
- Terminal tipo Bash (Linux, Mac o Git Bash en Windows)

## Levantar la app

Desde la raíz del proyecto, ejecutar en Bash / Git Bash:

```bash
chmod +x run.sh      # (Solo la primera vez en Linux)
./run.sh
```


### A tener en cuenta!

Me tomé el atrevimiento de sumar algunas pocas cosas:

- El atributo id ahora es un ***UUID*** generado y con un formato validado (en vez de Integer incremental)
- Se agrega un atributo ***boolean isActive*** para implementar soft/hard delete
- El atributo price pasa a ser ***Money money***, Value Object con su currency y value para disponer de 
relaciones y las consecuencias de éstas
- Product ***Timestamps***


# Análisis y diseño de la aplicación

Debido a la simplicidad requerida en el enunciado, no se presentarán modelos de clases de diseño
o diagramas de transición de estados, ni un análisis funcional riguroso.
Se apuesta a lograr una aplicación SIMPLE, que resuelva el enunciado de una manera elegante, basada en buenas prácticas,
utilizando patrones de diseño, principios SOLID, DRY, Inmutabilidad, Single Responsability y otros.

De todas maneras, el desarrollo estará guiado según metodologías ágiles (SCRUM), aunque sin hacer
hincapié en el detallismo o ahondar en este aspecto ya que no es requerido y conlleva tiempo.
Las estimaciones y épicas no son rigurosas y las historias de usuario no tienen un comentario, actores ni
especificaciones puntuales.

El desarrollo tomará solo un Sprint de 7 días (máximo) y será gestionado en Jira:

### Jira

https://wnorowsky.atlassian.net/jira/software/projects/ET/boards/38/backlog (deberían tener acceso público)

Al final del docuemnto, junto a la retrospectiva, se presentará el ***Burndown chart***.


## Criterios de validación

| ***Product***
- id (UUID): autogenerado de 32 caracteres
- name (String): mínimo 3 caracteres, máximo 50 y no puede ser null o espacios
- description (String): máximo 200 caracteres, permite null
- money (Money): value object Money
- quantity (Integer): debe ser un número positivo

| ***Money***
- currency (Currency): acepta valores "USD" | "ARS"
- value (Double): debe ser un número positivo

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
- **Gestión de configuración:** `application.yml` / `.env`
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
Creo que la arquitectura elegida es ***suficiente***, de lo contrario el costo de desarrollo aumentaría de forma
innecesaria.
Se seguirá además el principio code-first para generar la db desde el código.


### Patterns

Realmente hay varios de ellos que por supuesto se implementan de manera implícita, se discrimina solo algunos junto a
los implementandos explícitamente para cubrir una necesidad técnica o por buenas prácticas de desarrollo escalable y de bajo
acoplamiento.

- Adapter: de manera implícita, MapStruct actúa como adapter entre los DTOs y el modelo de dominio (en un sistema más
  complejo encapsularía la dependencia detrás de una interfaz para permitir el cambio de estrategia de mapeo pero no lo veo
  necesario aquí).
- Builder: al recuperar un producto desde DB, construye un product entity con todos sus atributos.
- Data Access Object: de manera implícita por ejemplo en JPA Repository
- Data Transfer Object: los DTOs implementados desacoplan la presentación de la entidad
- Factory Method: al crear un nuevo producto, con sólo los atributos necesarios provenientes de la request.
- Repository: para acceder a la base de datos evitando acoplamiento con la infrastructura.


### Dependencias

Sólo se discriminan aquellas de índole utilitaria (no las del ecosistema de Spring Boot o la db)

- MapStruct: para mapear
- Spring dev tools: para live reload
- Spring-dotenv: para acceder a los .env (aunque también se podría hacer de forma nativa)
- Lombok: para logger y algunos decoradores de clase para reducir código boilerplate


### Exceptions

Se implementarán handlers global y personalizados (bad request, validation y not found), con su log
correspondiente y la información pertinente para el posterior debug.
Las clases de cada excepción personalizada heredan de la clase abstracta ApiException que a su vez
hereda de RunTimeException y le pasa a través del super el message para que este lo guarde y poder
recuperarlo luego en el handler.

***Error cases***

- Para respuestas HTTP -> ApiException y @ControllerAdvice
- Por errores controlados que no interrumpan el flujo -> try-catch
- Por errores de librería -> try-catch y ApiException
- Los errores inesperados los captura Spring


## Documentación

Además del presente Readme, se seguirán buenas prácticas de código auto-documentado,
comentarios javaDocs estandarizados, commits continuos y Swagger.
No se implementará mucha configuración para Swagger, en una aplicación de mayor peso
se gestionaría en un clase una personalización mayor de la información presentada y las
respuestas, como del proyecto en general y su autoría, agrupación de paths relacionados, etc.


## API Response

Se implementa una plantilla de respuesta standard para consistencia en el front.
El campo data es un array, ya que en caso de no devolver datos no llega un null sino []
y a la vez está preparado para gestionar listas.
La response expone a través de sus métodos success / failure un objeto de tipo:

```
    {
        boolean success,
        String message,
        List<T> data
    }
```
