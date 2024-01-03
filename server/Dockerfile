# Estágio 1: Construção do aplicativo com Maven
FROM maven:3.8.1-openjdk-15-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn package -DskipTests

# Estágio 2: Imagem mínima para execução
FROM openjdk:15-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


