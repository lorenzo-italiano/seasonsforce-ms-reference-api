# Using Amazon Corretto image (20)
FROM amazoncorretto:20

RUN mkdir /app

# Define the working directory
WORKDIR /app

# Copy the jar file into the container
COPY seasonsforce-ms-reference-api-1.0-SNAPSHOT.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]