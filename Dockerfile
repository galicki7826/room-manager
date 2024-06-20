FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app
COPY . .
RUN ./mvnw package

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/room-occupancy-manager-0.0.1-SNAPSHOT.jar room-occupancy-manager-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/room-occupancy-manager-0.0.1-SNAPSHOT.jar"]