FROM gradle:8.10.2-jdk21 AS builder

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle/ gradle/

COPY . .

RUN gradle :application:bootJar --no-daemon

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/application/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xms512m", "-Xmx768m", "-jar", "app.jar"]
