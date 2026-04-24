# ─────────────────────────────────────────────
# Stage 1: Build
# ─────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cache dependencies layer separately for faster rebuilds
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ─────────────────────────────────────────────
# Stage 2: Run  (lightweight Alpine JRE image)
# ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp

COPY --from=build /app/target/*.jar app.jar

# Render assigns the port via the PORT env var; Spring reads ${PORT:8080}
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
