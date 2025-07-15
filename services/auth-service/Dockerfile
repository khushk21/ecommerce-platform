# Use official Java image
FROM openjdk:17-jdk-slim

# Add a volume to hold logs
VOLUME /tmp

# Set working directory
WORKDIR /app

# Copy JAR file built by Maven
COPY target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar

# Expose port
EXPOSE 8081

# Run the JAR
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
