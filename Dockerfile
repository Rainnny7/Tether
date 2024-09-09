# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Set the working directory
WORKDIR /home/container

# Copy the current directory contents into the container at /home/container
COPY . .

# Build the jar
RUN mvn package -T2C -Dmaven.test.skip -DskipTests

# Stage 2: Create the final lightweight image
FROM eclipse-temurin:21-jre-alpine

# Install Git
RUN apt-get update && apt-get install git -y

# Set the working directory
WORKDIR /home/container

# Copy the built jar file from the builder stage
COPY --from=builder /home/container/target/Tether.jar .

# We're running in production
ENV APP_ENV "production"

# Run the jar file
CMD java -jar Tether.jar -Djava.awt.headless=true