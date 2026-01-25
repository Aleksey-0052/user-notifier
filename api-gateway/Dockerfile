# Упрощенный вариант сборки образа приложения
FROM maven:3.9.6-eclipse-temurin-21-jammy
ADD target/*.jar api-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]

# EXPOSE 8080 - не влияет на приложение, нужна IT-специалисту