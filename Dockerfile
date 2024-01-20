# Verwenden Sie das offizielle OpenJDK-Image als Basis
FROM openjdk:21-jdk-bullseye

# Install required utilities
RUN apt-get update && apt-get install -y findutils

# Setzen Sie das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopieren Sie die Gradle-Konfigurationsdateien zuerst (für bessere Caching-Layer)
COPY build.gradle.kts settings.gradle.kts /app/

# Kopieren Sie den Gradle Wrapper und andere Projektdateien
COPY gradlew /app/
COPY gradle /app/gradle
COPY src /app/src

# Bauen Sie das Projekt
RUN ./gradlew build

# Ausführen der Anwendung
CMD ["./gradlew", "run"]
