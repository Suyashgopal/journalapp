# Journal App

A secure REST API for personal journaling built with Spring Boot.

The application allows users to create and manage private journal entries, authenticate securely using JWT, track moods through sentiment analysis, and receive weekly mood summaries using asynchronous processing.

---

## Features

- User registration and authentication using JWT
- Secure password hashing with BCrypt
- Role-based authorization (USER / ADMIN)
- Personal journal CRUD operations
- User-specific access control for journal entries
- Sentiment tracking for journal records
- Weekly mood summary emails
- Asynchronous processing using Apache Kafka
- Weather greeting API with Redis caching
- Centralized exception handling
- Request validation using DTOs
- Swagger API documentation

---

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 11 |
| Framework | Spring Boot |
| Security | Spring Security, JWT |
| Database | MongoDB |
| ORM/ODM | Spring Data MongoDB |
| Cache | Redis |
| Messaging | Apache Kafka |
| Email | Spring Mail |
| Documentation | Swagger / OpenAPI |
| Build Tool | Maven |
| Utilities | Lombok |

---

# System Architecture

The application follows a layered backend architecture:

```text
Client
  |
  |
JWT Authentication Filter
  |
  |
Controller Layer
  |
  |
Service Layer
  |
  |
Repository Layer
  |
  |
MongoDB
```

Additional services:

```text
Scheduler
    |
    |
Apache Kafka
    |
    |
Email Service


Weather API
    |
    |
Redis Cache
```

---

# Project Structure

```text
journal-app

├── src/main/java/com/springproj/journalApp
│
├── controller
│   ├── Public APIs
│   ├── User APIs
│   ├── Journal APIs
│   └── Admin APIs
│
├── service
│   ├── Business Logic
│   ├── Authentication
│   ├── Email Processing
│   └── External API Handling
│
├── repository
│   └── MongoDB Data Access
│
├── entity
│   └── Database Models
│
├── dto
│   └── Request/Response Objects
│
├── config
│   └── Security, Redis, Swagger Configuration
│
├── filter
│   └── JWT Request Filter
│
├── scheduler
│   └── Background Jobs
│
├── docs
│   ├── HLD.md
│   └── LLD.md
│
├── pom.xml
└── README.md
```

---

# System Design Documentation

Detailed architecture documentation is available inside the `docs` folder.

## High Level Design (HLD)

Covers complete system architecture:

- System overview
- Architecture diagram
- Request flow
- Authentication flow
- Kafka workflow
- Database interactions
- Redis caching flow
- External integrations
- Deployment overview

[View HLD](docs/HLD.md)

---

## Low Level Design (LLD)

Covers implementation details:

- Package structure
- Entity design
- DTO design
- Controller responsibilities
- Service layer logic
- Repository implementation
- JWT security flow
- Redis implementation
- Error handling
- Class responsibilities

[View LLD](docs/LLD.md)

---

# How It Works

## Authentication Flow

```text
User Login
    |
Credentials Verification
    |
JWT Token Generated
    |
Token Sent With Requests
    |
JWT Filter Validates Token
    |
Protected APIs Accessible
```

---

## Journal Flow

```text
User Request

      ↓

Controller

      ↓

Service

      ↓

Repository

      ↓

MongoDB
```

Each journal entry is linked with its owner so users can access only their own data.

---

## Weekly Sentiment Flow

```text
Scheduler

    ↓

Fetch User Entries

    ↓

Analyze Sentiments

    ↓

Publish Kafka Event

    ↓

Kafka Consumer

    ↓

Send Email Summary
```

---

# API Endpoints

Base URL:

```text
http://localhost:8081/journal
```

---

## Public APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/public/health-check` | Check server status |
| POST | `/public/signup` | Register user |
| POST | `/public/login` | Login and generate JWT |

Example signup:

```json
{
  "username": "suyash",
  "password": "password123",
  "email": "user@gmail.com"
}
```

---

# Journal APIs

Requires authentication.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/journal` | Get user journals |
| POST | `/journal` | Create journal |
| GET | `/journal/id/{id}` | Get journal by ID |
| PUT | `/journal/id/{id}` | Update journal |
| DELETE | `/journal/id/{id}` | Delete journal |

Example:

```json
{
  "title": "My Day",
  "content": "Completed Spring Boot project"
}
```

---

# User APIs

Requires authentication.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/user/new` | Weather greeting |
| PUT | `/user` | Update account |
| DELETE | `/user` | Delete account |

---

# Admin APIs

Requires ADMIN role.

| Method | Endpoint | Description |
|---|---|---|
| GET | `/admin` | Get all users |
| POST | `/admin/create-admin-user` | Create admin |

---

# Database Design

## Users Collection

```text
users

id
username
password
email
roles
sentimentAnalysis
journalEntries
```

---

## Journal Entries Collection

```text
journal_entries

id
title
content
date
sentiment
```

Relationship:

```text
One User  →  Multiple Journal Entries
```

---

# Environment Variables

Create a `.env` file:

```env
MONGODB_URI=your_database_url

JWT_SECRET=your_secret

REDIS_URI=redis_url

WEATHER_API_KEY=weather_key

MAIL_USERNAME=email

MAIL_PASSWORD=password

KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

`.env` should never be committed.

---

# Running Locally

Clone the repository:

```bash
git clone <repo-url>

cd journalApp
```

Run application:

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/Mac:

```bash
./mvnw spring-boot:run
```

Application:

```text
http://localhost:8081/journal
```

Swagger:

```text
http://localhost:8081/journal/swagger-ui/index.html
```

---

# Future Improvements

- Docker containerization
- CI/CD pipeline
- Refresh token support
- More test coverage
- Advanced sentiment analysis
- Monitoring and logging

---

# Author

**Suyash Gopal**

Backend project demonstrating Spring Boot, JWT security, MongoDB, Redis caching, Kafka messaging, and scalable REST API design.
