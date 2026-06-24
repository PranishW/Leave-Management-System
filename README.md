# Leave Management System - Microservices Architecture

A distributed Leave Management System built using Spring Boot Microservices.

## Architecture

```text
                          +------------------+
                          |   React Client   |
                          +--------+---------+
                                   |
                                   v
                          +------------------+
                          |    API Gateway   |
                          |      :8765       |
                          +--------+---------+
                                   |
          -------------------------------------------------
          |                                               |
          v                                               v

+----------------------+                  +----------------------+
|   Employee Service   | <--- Feign ---> |    Leave Service     |
|       :8081          |                  |       :8080          |
+----------------------+                  +----------------------+
| Authentication       |                  | Leave Management     |
| JWT Generation       |                  | Approval Workflow    |
| Employee Details     |                  | Kafka Producer       |
+----------------------+                  +----------------------+
                                                       |
                       Kafka Events -------------------
                                   |
                                   v
                        +----------------------+
                        | Email Service        |
                        |       :8082          |
                        +----------------------+
                        | Kafka Consumer       |
                        | Email Notifications  |
                        +----------------------+

                                   |
                                   v
                        +----------------------+
                        |    Eureka Server     |
                        |       :8761          |
                        +----------------------+
```

---

# Tech Stack

* Java 17
* Spring Boot
* Spring Security
* Spring Cloud
* Eureka Discovery Server
* API Gateway
* OpenFeign
* Apache Kafka
* JWT Authentication
* Spring Data JPA / Hibernate
* MySQL
* JavaMailSender

---

# Services

## Employee Service

Handles:

* Authentication
* JWT Token Generation
* Employee Details
* Manager Details

## Leave Service

Handles:

* Employee Leave Application
* Employee Leave Balance
* Leave Approval / Rejection
* Manager Leave Requests Operations
* Kafka Event Publishing

## Email Service

Handles:

* Kafka Event Consumption
* Email Notifications

## Eureka Server

Handles:

* Service Registry & Discovery

## API Gateway

Handles:

* Centralized Routing
* Dynamic Service Discovery

---


JWT contains custom claims:

* username
* role
* empId

---

# Communication

## Synchronous Communication

Using OpenFeign:

## Asynchronous Communication

Using Kafka:

---

# Databases

Each microservice owns its own database.

* Employee DB
* Leave Balance DB
* Leave Requests DB

---

# Current Features

* JWT Authentication
* Leave Application
* Leave Approval/Rejection
* Manager Dashboard APIs
* Feign Client Communication
* Kafka-based Email Notifications
* Eureka Service Discovery
* API Gateway Routing

---

# Ports

| Service              | Port |
| -------------------- | ---- |
| Eureka Server        | 8761 |
| API Gateway          | 8765 |
| Employee Service     | 8081 |
| Leave Service        | 8080 |
| Email Service        | 8082 |

---

# Future Improvements

* React Frontend
* Refresh Tokens
* HttpOnly Cookie Authentication
* Docker & Kubernetes
* Distributed Logging
* Monitoring & Tracing
* Circuit Breakers

---
