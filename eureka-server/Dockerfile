# Упрощенный вариант сборки образа приложения
FROM maven:3.9.6-eclipse-temurin-21-jammy
ADD target/*.jar eureka-server.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/eureka-server.jar"]