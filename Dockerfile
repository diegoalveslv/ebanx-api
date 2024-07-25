FROM eclipse-temurin:21-alpine

WORKDIR /app

COPY build/libs/ebanx-api-0.0.1-SNAPSHOT.jar /app/ebanx-api-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","ebanx-api-0.0.1-SNAPSHOT.jar"]