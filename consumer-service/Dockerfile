FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY . .

RUN ./mvnw -B clean install -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/consumer-service/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
