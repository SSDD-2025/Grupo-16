# Stage 1: Construction
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder

# Work directory where all the commands will be executed.
WORKDIR /project

COPY ./pom.xml /project/

RUN mvn dependency:go-offline
COPY ./src /project/src

# Execute the project compilation and download the needed dependencies.
RUN mvn -B package -DskipTests

# Phase 2: Execution Container
FROM eclipse-temurin:21-jre-alpine

# Work directory for the application.
WORKDIR /usr/src/app

# Copy the .jar file generated in the Construction Stage (Stage 1).
COPY --from=builder /project/target/entrega1-1.0.0.jar /usr/src/app/app.jar

# HTTPS.
EXPOSE 8443

# Command to run the application with SSL enabled.
CMD ["java", "-jar", "app.jar"]