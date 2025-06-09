FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY producer-service/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
