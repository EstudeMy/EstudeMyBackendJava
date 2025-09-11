FROM ubuntu:latest
LABEL authors="Alunos"

ENTRYPOINT ["top", "-b"]

# ================================
# Etapa 1: Build da aplicação
# ================================
FROM gradle:8.10.0-jdk21 AS build
WORKDIR /app

# Copiar os arquivos do projeto
COPY . .

RUN ./mvnw clean package -DskipTests


# ================================
# Etapa 2: Rodar a aplicação
# ================================
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiar o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar app.jar


# Variável de ambiente (porta do Spring Boot)
ENV PORT=8080

# Expor a porta do container
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
