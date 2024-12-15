# Use a multi-stage build to keep the final image lightweight
# Stage 1: Build the application
FROM gradle:7.5.1-jdk17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy only the Gradle wrapper and build files
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

# Give execute permission to the gradlew script
RUN chmod +x ./gradlew

# Download Gradle dependencies
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the source code
COPY src /app/src

# Build the application
RUN ./gradlew build -x test --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk-jammy

# Set a working directory for the application
WORKDIR /app

# Copy the Spring Boot JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
