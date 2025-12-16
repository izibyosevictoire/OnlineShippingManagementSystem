# Dockerization Guide for Online Shipping System

## Overview
This application is fully dockerized and can be run in containerized environments. The project includes:
- `Dockerfile` - Containerizes the Spring Boot application
- `docker-compose.yml` - Sets up PostgreSQL database container

## Prerequisites
- Docker installed and running
- Docker Compose installed
- Maven (for building the JAR file)

## Building and Running

### Step 1: Build the Application JAR
```bash
./mvnw clean package
```
This creates `target/shipping-system-0.0.1-SNAPSHOT.jar`

### Step 2: Start PostgreSQL Database
```bash
docker compose up -d
```
This starts a PostgreSQL 16 container with:
- Database: `shippingdb`
- User: `postgres`
- Password: `12345`
- Port: `5432`

### Step 3: Build Docker Image for Application
```bash
docker build -t shipping-system-app .
```

### Step 4: Run the Application Container
```bash
docker run --name shipping-app \
  --network shipping-system_default \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/shippingdb \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=12345 \
  -p 8080:8080 \
  shipping-system-app
```

Or using docker-compose (if you add the app service):
```bash
docker compose up
```

## Dockerfile Explanation
The `Dockerfile` uses:
- Base image: `eclipse-temurin:21-jre-alpine` (lightweight Java 21 runtime)
- Copies the built JAR file into the container
- Exposes port 8080 for the web application
- Runs the Spring Boot application with `java -jar`

## Docker Compose Explanation
The `docker-compose.yml` defines:
- PostgreSQL 16 database service
- Persistent volume for database data
- Network configuration for container communication

## Verification
Once running, access the application at:
- Web UI: http://localhost:8080
- Database: localhost:5432 (from host) or `postgres:5432` (from other containers)

## Troubleshooting
- If port 5432 is already in use, modify `docker-compose.yml` to use a different port (e.g., `5433:5432`)
- Ensure Docker daemon is running: `sudo systemctl start docker`
- Check container logs: `docker logs shipping-postgres` or `docker logs shipping-app`

