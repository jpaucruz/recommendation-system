FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/recommendation-system-ms-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["/bin/bash", "-c", "sleep 30 && exec java -jar app.jar"]