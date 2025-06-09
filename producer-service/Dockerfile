FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw -B clean install -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/producer-service/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
