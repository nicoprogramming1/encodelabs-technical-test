# CONSTRUCCIÓN

FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

# descarga dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# construye el jar desde el codigo
COPY src ./src
RUN mvn clean package -DskipTests

# EJECUCIÓN
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# va a hacer falta curl para el health
RUN apk add --no-cache curl

# agregamos un user nuevo (no root)
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# copiamos el jar que contruimos antes (compilado)
COPY --from=build /app/target/encodelabs-0.0.1-SNAPSHOT.jar app.jar

# cambiamos la propiedad de la carpeta /app al usuario nuevo (por seguridad ->
# esto es buena práctica ya que en Linux el user es root)
RUN chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

# Health
HEALTHCHECK --interval=10s --timeout=7s --start-period=15s --retries=3 \
  CMD curl -f http://localhost:8080/api/actuator/health || exit 1

# Start !!!
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

