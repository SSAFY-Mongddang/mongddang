# Build stage
FROM gradle:jdk21-jammy AS builder
WORKDIR /build
COPY . /build/
RUN gradle build -x test

# Run stage
FROM openjdk:21-oracle AS test
WORKDIR /app
EXPOSE 8080
ARG PROFILE
COPY --from=builder /build/build/libs/*.jar app.jar
ENV TZ=Asia/Seoul
ENV SPRING_PROFILES_ACTIVE=${PROFILE}
ENTRYPOINT ["java", "-jar","/app/app.jar"]

# Run stage for jenkins
FROM openjdk:21-oracle AS prod
EXPOSE 8080
ARG PROFILE
ARG JAR_FILE=build/libs/*.jar
WORKDIR /app
COPY ${JAR_FILE} ./app.jar
# COPY src/main/resources/certification.json ./resources/certification.json
ENV TZ=Asia/Seoul
ENV SPRING_PROFILES_ACTIVE=${PROFILE}
ENTRYPOINT ["java", "-jar","./app.jar"]