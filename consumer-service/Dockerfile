FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build
COPY . .
RUN mvn clean package -pl consumer-service -am -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/consumer-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
