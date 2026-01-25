# build project
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# runtime project
# С одного этапа на другой копируются только нужные нам артефакты
FROM eclipse-temurin:21-jre
LABEL authors="Semenikhin A.F."
WORKDIR /app
COPY --from=build /app/target/*.jar email-notification-microservice.jar
ENTRYPOINT ["java", "-jar", "/email-notification-microservice.jar"]
