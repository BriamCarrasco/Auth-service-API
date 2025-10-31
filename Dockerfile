# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Run
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Copia el properties y el wallet desde el contexto del proyecto
COPY src/main/resources/application.properties ./application.properties
COPY Wallet_FULLSTACKIII ./Wallet_FULLSTACKIII

EXPOSE 8082

ENV SPRING_CONFIG_LOCATION=./application.properties

ENTRYPOINT ["java", "-jar", "app.jar"]