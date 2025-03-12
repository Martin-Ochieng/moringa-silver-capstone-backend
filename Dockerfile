# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the target directory
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (Render dynamically assigns a PORT)
EXPOSE 8080

# Set the PORT environment variable for Render
CMD ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]
