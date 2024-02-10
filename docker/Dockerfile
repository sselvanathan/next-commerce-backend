# Stage 1: Build the application
# Name this stage as 'builder'
FROM amazoncorretto:21-alpine as builder

# Install required utilities
RUN apk update && apk add --no-cache findutils

# Set the working directory in the container
WORKDIR /app

# Copy Gradle configuration files first (for better caching layer)
COPY build.gradle.kts settings.gradle.kts /app/

# Copy the Gradle Wrapper and other project files
COPY gradlew /app/
COPY gradle /app/gradle
COPY src /app/src

# Build the project
# If you encounter any issues during the build, consider adding more specific build dependencies or troubleshooting compatibility issues.
RUN ./gradlew build -x test --no-daemon

# Stage 2: Create the runtime image
FROM amazoncorretto:21-alpine

# Copy the built JAR from the 'builder' stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Set the working directory
WORKDIR /app

# Run the JAR file (specify the exact name if there are multiple JAR files)
CMD ["java", "-jar", "app.jar"]