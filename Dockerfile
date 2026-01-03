# ===============================
# Build Stage
# ===============================
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

# 1️⃣ Copy Maven wrapper + config
COPY mvnw .
COPY .mvn .mvn

# 2️⃣ Give execute permission
RUN chmod +x mvnw

# 3️⃣ Copy pom.xml FIRST (IMPORTANT)
COPY pom.xml .

# 4️⃣ Download dependencies (now pom.xml exists ✅)
RUN ./mvnw dependency:go-offline

# 5️⃣ Copy source code
COPY src src

# 6️⃣ Build jar (skip tests)
RUN ./mvnw clean package -DskipTests


# ===============================
# Run Stage
# ===============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
