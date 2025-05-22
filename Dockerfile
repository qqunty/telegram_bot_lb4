FROM gradle:8.5-jdk17-alpine AS builder
WORKDIR /workspace
COPY . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /workspace/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]