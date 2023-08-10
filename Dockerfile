# Use the official maven/Java 17 base image
FROM maven:3.8.4-openjdk-17 AS build

# Set the current working directory inside the image
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Layering helps in reusing the cached layers for dependencies
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src src/

# Package the application
RUN mvn package -DskipTests

# Use the official OpenJDK runtime base image
FROM openjdk:17-jdk-slim-buster

# Set the application's working directory in the container
WORKDIR /app

# Copy the jar file built in the previous stage
COPY --from=build /app/target/*.jar app.jar

# Specify the default command to run the application
CMD ["java", "-jar", "app.jar"]
