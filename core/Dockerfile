# Упрощенный вариант сборки образа приложения
FROM maven:3.9.6-eclipse-temurin-21-jammy
ADD target/*.jar core.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/core.jar"]