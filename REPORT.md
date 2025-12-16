## Online Shipping Management System – Final Project Report

### 1. Introduction

This project implements an **online shipping management system** for a small e‑commerce shop.  
The system allows staff to create shipments, track their status through different stages, and automatically notify customers whenever a shipment status changes.

The application is built as a web application using **Java 21** and **Spring Boot**, backed by a **PostgreSQL** database and containerized with **Docker**.  
It follows a **layered MVC architecture** and applies the **Observer design pattern** to keep the notification logic loosely coupled from the shipment entity.

### 2. Problem Statement and Objectives

Many small businesses track parcel shipments manually (for example using spreadsheets or paper notes).  
This leads to:
- Lost or duplicated records  
- No central dashboard of all shipments  
- No automatic customer notification when a parcel moves from one status to another  

**Objectives of the project:**
- Provide a simple web dashboard to **create and view shipments**.  
- Allow staff to **update shipment statuses** (PENDING → PICKED_UP → IN_TRANSIT → DELIVERED).  
- **Automatically notify customers** (simulated by the console) on status changes using the Observer pattern.  
- Demonstrate **clean Java coding practices**, basic **testing**, **Git/GitHub usage**, and **Dockerization**.

### 3. System Design and Architecture

The system is designed using a classic **three‑layer architecture** inside a Spring Boot MVC application:

- **Presentation layer (Web / MVC)**  
  - `ShipmentController` handles HTTP requests and returns Thymeleaf views such as `shipment/dashboard.html`.  
  - Users can create shipments and change their status from the browser.

- **Business / Service layer**  
  - `ShipmentService` contains the main business logic to list, create and update shipments.  
  - `EmailNotificationService` reacts whenever a shipment status changes and prints a simulated email notification.

- **Data access layer**  
  - JPA entities `Order` and `Shipment` model the domain data.  
  - `OrderRepository` and `ShipmentRepository` manage persistence to a PostgreSQL database using Spring Data JPA.

The application uses **Spring Boot autoconfiguration** to wire these layers together and exposes them over HTTP on port 8080.

Three UML‑style diagrams are provided separately in the `diagrams/` folder:
- **Activity Diagram**: Shipment lifecycle from creation to delivery.  
- **Data Flow Diagram**: How data moves between user, web layer, services and database.  
- **Sequence Diagram**: Detailed interaction when updating shipment status and notifying observers.

### 4. Design Pattern Used – Observer Pattern

The main design pattern used in this project is the **Observer pattern**.

- The `Shipment` entity plays the role of the **Subject**. It maintains a list of `ShipmentStatusObserver` instances.  
- `EmailNotificationService` implements the `ShipmentStatusObserver` interface and subscribes to a `Shipment`.  
- When the method `setStatus(String newStatus)` is called on a `Shipment`, the shipment:
  1. Updates its own status and timestamps.  
  2. Calls `notifyObservers()`, which invokes `onStatusChanged(this)` on each registered observer.  
- `EmailNotificationService` then prints a message to the console simulating that an email has been sent to the customer about the new status.

**Benefits of using Observer:**
- The shipment model is **decoupled** from the notification mechanism.  
- New notification channels (for example SMS, push notifications or logging) can be added as new observers **without changing** the existing `ShipmentService` logic.  
- This demonstrates extensibility and adherence to the **Open/Closed Principle**.

### 5. Implementation Overview and Coding Practices

**Technology stack:**
- Java 21  
- Spring Boot (Web, Data JPA, Security, Thymeleaf, Validation)  
- PostgreSQL (production / Docker), H2 (optional for development)  
- Maven for build and dependency management  
- JUnit 5 and Mockito for testing  
- Docker and Docker Compose for containerization

**Package organization (clean architecture):**
- `config` – security and other configuration classes  
- `controller` – MVC controllers (`ShipmentController`)  
- `service` – business services (`ShipmentService`, `EmailNotificationService`)  
- `entity` – JPA entities (`Order`, `Shipment`)  
- `repository` – Spring Data JPA repositories (`OrderRepository`, `ShipmentRepository`)  
- `observer` – Observer interfaces and implementations (`ShipmentStatusObserver`, `EmailNotificationService`)

**Best practice highlights:**
- **Separation of concerns**: Controllers handle web requests, services contain business logic, and repositories manage database access.  
- **Clean naming**: Classes and methods have clear, descriptive names (`createShipment`, `updateStatus`, `findAll`).  
- **Use of Lombok** (`@Data`) to minimize boilerplate while keeping fields private.  
- Based on standard Java and **Google‑style** conventions: camelCase for methods and variables, PascalCase for classes, clear package structure.

### 6. Version Control – Git and GitHub

The project uses **Git** for version control and can be hosted on **GitHub**.

Typical workflow:
1. Initialize repository  
   - `git init`  
2. First commit  
   - `git add .`  
   - `git commit -m "Initial Spring Boot shipping system"`  
3. Create GitHub repository and link it  
   - `git remote add origin <your-github-repo-url>`  
   - `git push -u origin main`  
4. Make small, meaningful commits, for example:  
   - `"Add Shipment entity and repositories"`  
   - `"Implement Observer pattern for shipment notifications"`  
   - `"Add Dockerfile and ShipmentService tests"`  
   - `"Update README and diagrams"`

Using Git in this way provides **history**, **backup**, and makes it easy to demonstrate progress on the project.

### 7. Testing and Quality Assurance

Testing is implemented using **JUnit 5**, **spring‑boot‑starter‑test**, and **Mockito**.

- `ShippingSystemApplicationTests` loads the Spring context to ensure beans are correctly wired.  
- `ShipmentServiceTest` is a unit test that uses mocks for `ShipmentRepository` and `EmailNotificationService` to test the service in isolation.

Key test scenarios:
- **Create shipment**: verify that `ShipmentService.createShipment()` calls `save()` on the repository and returns a non‑null object.  
- **Update status**: verify that `ShipmentService.updateStatus(id, newStatus)` changes the status to the specified value and persists it.

Example manual test cases:
1. **TC1** – Create shipment from dashboard  
   - Step: Open `http://localhost:8080`, click “Create Shipment”.  
   - Expected: New shipment appears in the list with status `PENDING`.
2. **TC2** – Update shipment to `IN_TRANSIT`  
   - Step: Select an existing shipment and update its status.  
   - Expected: Status changes in the UI, console prints an “email notification” message.
3. **TC3** – Deliver shipment  
   - Step: Change status to `DELIVERED`.  
   - Expected: Status updated, observer prints final notification.

These tests show that the core business logic and the Observer pattern behave as expected.

### 8. Dockerization and Deployment

The application is packaged as an executable JAR using Maven:

- Build command: `./mvnw clean package`

**Dockerfile**  
- Uses `eclipse-temurin:21-jre-alpine` as the base image.  
- Copies the generated `shipping-system-0.0.1-SNAPSHOT.jar` into the image.  
- Exposes port `8080`.  
- Runs the application with `java -jar`.

**PostgreSQL with Docker Compose**  
- `docker-compose.yml` defines a PostgreSQL 16 container with:
  - Database: `shippingdb`  
  - User: `postgres`  
  - Password: `12345`

**Typical run steps:**
1. Start PostgreSQL:  
   - `docker compose up -d`
2. Build application JAR:  
   - `./mvnw clean package`
3. Build Docker image:  
   - `docker build -t shipping-system-app .`
4. Run application container (example):  
   - `docker run --name shipping-system-app --net=host -e SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/shippingdb shipping-system-app`

This satisfies the requirement to **Dockerize** the application and run it in a containerized environment.

### 9. Diagrams Overview

The folder `diagrams/` contains three markdown files with detailed descriptions of the diagrams:
- `activity-diagram.md` – shows the shipment lifecycle from creation to delivery.  
- `data-flow-diagram.md` – shows how data flows between the user, web app, services and database.  
- `sequence-diagram.md` – shows the interaction when updating the shipment status and sending notifications.

These descriptions can be directly converted into graphical diagrams in PowerPoint, Draw.io, or any UML tool.

### 10. Conclusion

This project delivers a working **Online Shipping Management System** built with modern Java technologies.  
It addresses a real‑life problem of tracking shipments and notifying customers, and demonstrates:
- Layered MVC architecture with Spring Boot  
- The **Observer** design pattern in the shipment notification logic  
- Use of **Git** and clean coding practices  
- Basic automated tests for core services  
- **Dockerization** for easy deployment

The design is modular and can be extended in the future, for example by adding authentication, more detailed order management, or additional observer implementations such as SMS or push notifications.


