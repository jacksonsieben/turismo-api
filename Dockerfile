FROM eclipse-temurin:17-jdk-alpine


WORKDIR /app

COPY . .

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db-postgres:5432/db_turismo


RUN chmod +x gradlew
RUN ./gradlew build -x test

EXPOSE 8080

ENTRYPOINT ["java","-jar","build/libs/turismo-api-0.0.1.jar"]