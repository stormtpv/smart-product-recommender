FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY consumer-service/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
