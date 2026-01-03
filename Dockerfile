# Step 1: Use official Java 17 runtime
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Set working directory inside container
WORKDIR /app

# Step 3: Copy Maven wrapper and pom.xml for dependency download
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Step 4: Download dependencies offline
RUN ./mvnw dependency:go-offline

# Step 5: Copy the source code
COPY src ./src

# Step 6: Build the fat jar (skip tests for speed)
RUN ./mvnw package -DskipTests

# Step 7: Expose the default Spring Boot port
EXPOSE 8080

# Step 8: Run the jar
CMD ["java", "-jar", "target/personal_finance_manager-0.0.1-SNAPSHOT.jar"]
