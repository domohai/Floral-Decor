# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
# Copy dependencies specifications first for better caching
COPY pom.xml .
RUN mvn dependency:go-offline --batch-mode
# Copy and build source code
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM bellsoft/liberica-runtime-container:jre-17-musl
WORKDIR /app
# Create a non-root user
RUN addgroup --system appuser && adduser --system --ingroup appuser appuser
# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar
RUN chown appuser:appuser app.jar
# Switch to non-root user
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
