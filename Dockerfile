# Use an official Maven image to build the project
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use a lightweight JDK to run the app
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (Render dynamically assigns a PORT)
EXPOSE 8080

# Set the PORT environment variable for Render
CMD ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]
