FROM gradle:8.10.2-jdk21 AS builder

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle/ gradle/

COPY . .

RUN gradle :application:bootJar --no-daemon

FROM amazoncorretto:21.0.10

WORKDIR /app

COPY --from=builder /app/application/build/libs/*.jar app.jar

EXPOSE 8082

# Healthcheck usando Actuator
HEALTHCHECK --interval=30s --timeout=5s --start-period=50s --retries=3 \
  CMD curl -f http://localhost:8082/actuator/health || exit 1

ENTRYPOINT ["java", "-Xms512m", "-Xmx768m", "-jar", "app.jar"]
