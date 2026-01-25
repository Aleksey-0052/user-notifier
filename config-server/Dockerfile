# Упрощенный вариант сборки образа приложения
FROM maven:3.9.6-eclipse-temurin-21-jammy
ADD target/*.jar config-server.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/config-server.jar"]