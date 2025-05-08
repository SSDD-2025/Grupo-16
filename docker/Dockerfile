# Stage 1: Construction
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder

# Work directory where all the commands will be executed.
WORKDIR /project

COPY ./src /project/src
COPY ./pom.xml /project/

# Execute the project compilation and download the needed dependencies.
RUN mvn -B package -DskipTests

# Phase 2: Execution Container
FROM eclipse-temurin:21-jre-alpine

# Work directory for the application.
WORKDIR /usr/src/app

# Copy the .jar file generated in the Construction Stage (Stage 1).
COPY --from=builder /project/target/entrega1-1.0.0.jar /usr/src/app/app.jar

# Copy the keystore to enable the SSL.
COPY src/main/resources/keystore.jks /usr/src/app/keystore.jks

# HTTPS.
EXPOSE 8443

# Environment Variables for SSL.
ENV SERVER_SSL_KEY_STORE=/usr/src/app/keystore.jks
ENV SERVER_SSL_KEY_STORE_PASSWORD=password
ENV SERVER_SSL_KEY_PASSWORD=password

# Command to run the application with SSL enabled.
CMD ["java", "-Dserver.ssl.key-store=/usr/src/app/keystore.jks", "-Dserver.ssl.key-store-password=password", "-Dserver.ssl.key-password=password", "-jar", "app.jar"]