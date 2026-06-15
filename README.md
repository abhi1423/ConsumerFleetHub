# ConsumerFleetHub

## Overview

ConsumerFleetHub is a Spring Boot-based backend application that connects consumers with fleet and transportation services. The application enables users to create transportation requests, search for suitable vehicles, book trips, and manage transportation workflows through secure REST APIs.

The system implements JWT-based authentication and role-based authorization to ensure secure access to application resources.

---

## Features

### User Management

* Consumer registration and management
* Secure password encryption using BCrypt
* Role-based access control (RBAC)

### Authentication & Authorization

* JWT-based authentication
* Spring Security integration
* Protected API endpoints
* User role management

### Load Query Management

* Create transportation/load requests
* Remove pending load requests
* Retrieve active and pending load queries
* View consumers with pending transportation requests

### Vehicle Booking

* Search available vehicles
* Book vehicles for transportation
* Track request status
* Manage trip lifecycle

### Trip Management

* Start transportation requests
* Receive transporter responses
* Close completed trips
* Update request status

### Exception Handling

* Centralized exception handling
* Custom business exceptions
* Meaningful API error responses

---

## Tech Stack

| Technology      | Purpose                        |
| --------------- | ------------------------------ |
| Java 17         | Programming Language           |
| Spring Boot 3   | Application Framework          |
| Spring Security | Authentication & Authorization |
| JWT             | Token-Based Security           |
| Spring Data JPA | Database Access                |
| MySQL           | Relational Database            |
| Lombok          | Boilerplate Code Reduction     |
| Maven           | Dependency Management          |

---

## Project Structure

```text
src/main/java
├── configuration
│   ├── AppConfiguration
│   └── SecurityConfiguration
├── Controller
│   ├── ConsumerController
│   ├── JwtLoginController
│   └── GlobalExceptionHandler
├── DTOs
├── Entities
│   ├── Consumer
│   ├── LoadQuery
│   ├── RequestStatus
│   ├── RequestToTransporter
│   └── Role
├── Exceptions
├── Jwt
│   ├── JwtAuthenticationFilter
│   └── JwtHelper
├── Repositories
├── Services
└── ResponseDTOs
```

---

## Security Features

* JWT Token Generation and Validation
* Role-Based Authorization
* Password Encryption using BCrypt
* Secure API Access using Spring Security
* Stateless Authentication Mechanism

---

## Sample Workflow

1. User registers in the system.
2. User authenticates and receives a JWT token.
3. User creates a load query.
4. System retrieves suitable vehicles.
5. User books a vehicle.
6. Transporter responds to the request.
7. Trip is completed and closed.

---

## Getting Started

### Prerequisites

* Java 17+
* Maven 3.8+
* MySQL 8+

### Clone Repository

```bash
git clone https://github.com/abhi1423/ConsumerFleetHub.git
cd ConsumerFleetHub
```

### Configure Database

Update the database properties in:

```properties
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fleethub
spring.datasource.username=root
spring.datasource.password=password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## API Highlights

### Authentication

* Login and JWT Token Generation

### Consumer APIs

* Register Consumer
* Get Consumer Details
* Get All Consumers
* Create Load Query
* Remove Load Query

### Vehicle APIs

* Search Vehicles
* Book Vehicle

### Trip APIs

* Update Transporter Response
* Close Trip


---

## Author

**Abhinav Chandel**

Java | Spring Boot | REST APIs | Spring Security | JWT | MySQL | Microservices
