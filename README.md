# Journal App

A secure REST API for personal journaling built using Spring Boot.

The application allows users to create private journal entries, manage their accounts, track sentiments, and receive weekly mood summaries through asynchronous email processing.

## Features

- User registration and login with JWT authentication
- Secure password storage using BCrypt
- Role-based access control (USER / ADMIN)
- Create, read, update, and delete personal journal entries
- Sentiment tracking for journal entries
- Weekly sentiment summary emails using scheduled jobs and Kafka
- Weather-based greeting API with Redis caching
- Centralized exception handling
- API documentation using Swagger/OpenAPI

---

## Tech Stack

| Technology | Usage |
|-----------|-------|
| Java 11 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Security | Authentication and Authorization |
| JWT | Stateless Authentication |
| MongoDB | Database |
| Spring Data MongoDB | Database Operations |
| Redis | API Response Caching |
| Apache Kafka | Async Messaging |
| Maven | Build Tool |
| Lombok | Reducing Boilerplate Code |
| Swagger | API Documentation |

---

## Project Structure

```text
src/main/java/com/springproj/journalApp

├── controller
│   ├── publiccontroller.java
│   ├── usercontroller.java
│   ├── journalentrycontrollerv2.java
│   └── admincontroller.java
│
├── service
│   ├── userservice.java
│   ├── journalentryservice.java
│   ├── weatherservice.java
│   ├── EmailService.java
│   └── SentimentConsumerService.java
│
├── repository
│   ├── userentryrepo.java
│   └── journalentryrepo.java
│
├── entity
│   ├── user.java
│   └── journalentry.java
│
├── dto
├── config
├── filter
├── scheduler
├── exception
└── utils
```

The project follows a layered architecture:

```
Controller
    |
Service Layer
    |
Repository Layer
    |
MongoDB Database
```

---

# Application Flow

1. User registers or logs in.
2. After successful authentication, a JWT token is generated.
3. Every protected request passes through JWT validation.
4. Controllers handle API requests.
5. Services execute business logic.
6. Repositories interact with MongoDB.
7. Background jobs process sentiment summaries and send messages through Kafka.
8. Kafka consumers handle email delivery asynchronously.

---

# API Endpoints

Base URL:

```text
http://localhost:8081/journal
```

---

## Public APIs

### Health Check

```http
GET /public/health-check
```

Response:

```text
ok
```

---

### Register User

```http
POST /public/signup
```

Request:

```json
{
  "username": "suyash",
  "password": "password123",
  "email": "user@gmail.com",
  "sentimentAnalysis": true
}
```

---

### Login

```http
POST /public/login
```

Request:

```json
{
  "username": "suyash",
  "password": "password123"
}
```

Response:

```text
JWT Token
```

Use token:

```text
Authorization: Bearer <token>
```

---

# Journal APIs

Authentication required.

## Create Journal Entry

```http
POST /journal
```

Request:

```json
{
  "title": "My Day",
  "content": "Today was productive"
}
```

---

## Get All Entries

```http
GET /journal
```

---

## Get Entry By ID

```http
GET /journal/id/{id}
```

---

## Update Entry

```http
PUT /journal/id/{id}
```

Request:

```json
{
  "title": "Updated title",
  "content": "Updated content"
}
```

---

## Delete Entry

```http
DELETE /journal/id/{id}
```

---

# User APIs

Authentication required.

## Weather Greeting

```http
GET /user/new
```

Returns user greeting with cached weather information.

---

## Update User

```http
PUT /user
```

Request:

```json
{
  "username": "newUsername",
  "password": "newPassword"
}
```

---

## Delete Account

```http
DELETE /user
```

---

# Admin APIs

Requires ADMIN role.

## Get Users

```http
GET /admin
```

## Create Admin User

```http
POST /admin/create-admin-user
```

---

# Database Design

## Users Collection

Stores user account details.

| Field | Description |
|-|-|
| id | MongoDB ObjectId |
| username | Unique username |
| password | BCrypt encrypted password |
| email | User email |
| roles | User permissions |
| sentimentAnalysis | Weekly summary preference |
| journalentries | User journal references |

---

## Journal Entries Collection

Stores user journals.

| Field | Description |
|-|-|
| id | MongoDB ObjectId |
| title | Entry title |
| content | Entry content |
| date | Creation date |
| sentiment | Entry mood |

Relationship:

```text
One User → Multiple Journal Entries
```

---

# Environment Variables

Create a `.env` file:

```env
MONGODB_URI=your_mongodb_connection_string

JWT_SECRET=your_secret_key

REDIS_URI=redis://localhost:6379

WEATHER_API_KEY=your_weather_api_key

MAIL_USERNAME=your_email

MAIL_PASSWORD=your_app_password

KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

Never commit `.env` files.

---

# Running Locally

## Requirements

- Java 11+
- MongoDB
- Maven
- Redis (optional)
- Kafka (optional)

---

Clone repository:

```bash
git clone <repository-url>

cd journalApp
```

Run:

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/Mac:

```bash
./mvnw spring-boot:run
```

Application runs on:

```text
http://localhost:8081/journal
```

Swagger:

```text
http://localhost:8081/journal/swagger-ui/index.html
```

---

# Future Improvements

- Add refresh tokens
- Add Docker support
- Improve automated testing
- Add CI/CD pipeline
- Add real-time sentiment analysis
- Improve monitoring and logging

---

# Author

**Suyash Gopal**

Spring Boot backend project built for learning backend development concepts including authentication, databases, caching, messaging, and API design.
