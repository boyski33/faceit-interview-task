FROM openjdk:11
LABEL project="User Service"

WORKDIR /app
COPY pom.xml /app/
COPY .mvn /app/.mvn
COPY mvnw /app/
COPY ./src/main /app/src/main

RUN ./mvnw clean install -DskipTests

EXPOSE 8080

ENTRYPOINT java -jar target/users-service-0.0.1-SNAPSHOT.jar
