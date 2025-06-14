FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build
COPY . .
RUN mvn clean package -pl producer-service -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/producer-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
