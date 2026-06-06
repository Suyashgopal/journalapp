# High Level Design (HLD) — Journal App

## 1. System Overview

Journal App is a **multi-user journaling REST API** where each user can write private journal entries. The system performs **asynchronous sentiment analysis** on entries and delivers a **weekly mood digest** by email. It is designed as a single deployable Spring Boot service backed by cloud-managed infrastructure (MongoDB Atlas, Redis, Kafka).

---

## 2. Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────┐
│                          CLIENT (Browser / curl / Postman)          │
└───────────────────────────────────┬─────────────────────────────────┘
                                    │ HTTPS  (port 8081, /journal)
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                     SPRING BOOT APPLICATION                         │
│                                                                     │
│  ┌──────────────┐   ┌──────────────┐   ┌──────────────┐            │
│  │  JWT Filter  │──▶│  Controllers │──▶│   Services   │            │
│  └──────────────┘   └──────────────┘   └──────┬───────┘            │
│                                               │                     │
│            ┌──────────────────────────────────┼──────────────────┐  │
│            │                                  │                  │  │
│            ▼                                  ▼                  ▼  │
│     ┌─────────────┐                  ┌──────────────┐  ┌────────┐  │
│     │  Scheduler  │                  │  Repositories│  │ Redis  │  │
│     │  (weekly    │                  │  (Spring Data│  │Service │  │
│     │   cron)     │                  │   MongoDB)   │  └───┬────┘  │
│     └──────┬──────┘                  └──────┬───────┘      │       │
│            │                                │              │       │
└────────────┼────────────────────────────────┼──────────────┼───────┘
             │                                │              │
             ▼                                ▼              ▼
     ┌──────────────┐               ┌──────────────┐  ┌──────────────┐
     │    KAFKA     │               │   MONGODB    │  │    REDIS     │
     │   Broker     │               │    Atlas     │  │    Cache     │
     │  (topic:     │               │  (users +    │  │  (weather    │
     │  weekly-     │               │   entries)   │  │   TTL 300s)  │
     │  sentiments) │               └──────────────┘  └──────────────┘
     └──────┬───────┘
            │ (consumed by SentimentConsumerService)
            ▼
     ┌──────────────┐
     │  EMAIL (SMTP)│
     │  Gmail       │
     └──────────────┘
```

---

## 3. Core Components

| Component | Technology | Responsibility |
|---|---|---|
| REST API | Spring Boot Web | Receives and responds to HTTP requests |
| Auth Layer | Spring Security + JWT | Stateless authentication and role-based access |
| Persistence | MongoDB (Spring Data) | Stores users and journal entries |
| Caching | Redis | Caches Weatherstack API responses (TTL 5 min) |
| Messaging | Apache Kafka | Async weekly sentiment digest pipeline |
| Email | Spring Mail (Gmail SMTP) | Delivers weekly mood summaries to users |
| Scheduler | Spring `@Scheduled` | Triggers weekly digest every night at 1 AM |
| API Docs | springdoc (Swagger UI) | Auto-generated, browsable API reference |

---

## 4. Request Flow

### 4.1 Authenticated Request (e.g. create journal entry)

```
Client
  │
  │  POST /journal  { "title": "...", "content": "..." }
  │  Authorization: Bearer <JWT>
  ▼
JwtFilter
  │  extracts username from token
  │  loads UserDetails from MongoDB
  │  sets SecurityContext
  ▼
Spring Security
  │  checks route is permitted for ROLE_USER
  ▼
JournalController
  │  validates DTO (@Valid)
  │  reads authenticated username from SecurityContext
  ▼
JournalEntryService  (@Transactional)
  │  saves entry to journal_entries collection
  │  appends DBRef to user's journalentries list
  │  saves updated user
  ▼
MongoDB
  │  writes two documents atomically (Mongo transaction)
  ▼
Client ← 201 Created + entry JSON
```

### 4.2 Public Request (login)

```
Client
  │  POST /public/login { username, password }
  ▼
PublicController
  │  AuthenticationManager.authenticate()
  │    → BCrypt verify against stored hash
  ▼
jwtutil.generateToken(username)
  │  signs HS256 JWT, 50 min expiry
  ▼
Client ← 200 OK + JWT string
```

### 4.3 Weekly Sentiment Flow (background)

```
Spring Scheduler (cron: 0 0 1 * * ?)
  │  fires every day at 01:00
  ▼
userscheduler.fetchUSERsendSAmail()
  │  queries MongoDB for users with sentimentAnalysis=true
  │  and a valid email address
  │
  │  for each user:
  │    filter entries from last 7 days
  │    tally sentiment enum counts
  │    find most frequent sentiment
  ▼
KafkaTemplate.send("weekly-sentiments", email, SentimentData)
  │
  ▼  (if Kafka fails → fallback)
EmailService.sendemail()  ◄──── direct SMTP
  │
  ▼  (normal path via Kafka)
SentimentConsumerService
  │  @KafkaListener(topic = "weekly-sentiments")
  ▼
EmailService.sendemail(to, subject, body)
```

---

## 5. Data Flow Diagram

```
User ──▶ [POST /public/signup] ──▶ MongoDB.users
User ──▶ [POST /public/login]  ──▶ MongoDB.users (read) ──▶ JWT ──▶ User

User ──▶ [POST /journal]       ──▶ MongoDB.journal_entries (write)
                                └─▶ MongoDB.users.journalentries (append DBRef)

User ──▶ [GET /user/new]       ──▶ Redis (cache hit?) ──yes──▶ response
                                └──no──▶ Weatherstack API ──▶ Redis.set(TTL 300s) ──▶ response

Scheduler ──▶ MongoDB.users (read opted-in)
           ──▶ Kafka topic: weekly-sentiments
           ──▶ Consumer ──▶ Gmail SMTP ──▶ User inbox
```

---

## 6. Security Design

| Concern | Approach |
|---|---|
| Password storage | BCrypt (Spring Security default cost factor 10) |
| Authentication | Stateless JWT, signed with HS256, 50-minute expiry |
| Authorization | Method-level `@PreAuthorize` + route-level `antMatchers` |
| Secret management | All credentials in env vars / gitignored `.env`, never in source |
| Error responses | `GlobalExceptionHandler` — no stack traces exposed to clients |
| CORS | Explicit allow-list of origins in `CorsConfigurationSource` |
| Entry ownership | Controller verifies entry belongs to authenticated user before mutating |

---

## 7. Scalability Considerations

| Concern | Current approach | Production path |
|---|---|---|
| Database | Single MongoDB Atlas cluster | Atlas sharding / replica set reads |
| Cache | Single Redis | Redis Cluster or ElastiCache |
| Messaging | Single Kafka broker | Multi-broker Kafka cluster, partition by user ID |
| App | Single JAR | Horizontal scaling behind a load balancer (stateless JWT means no sticky sessions) |
| Admin listing | Paginated (`page`, `size`, max 100) | Covered |

---

## 8. External Integrations

| Service | Purpose | Failure handling |
|---|---|---|
| MongoDB Atlas | Primary data store | Spring retries; app fails to start if unreachable |
| Redis | Weather cache | Cache miss → calls Weatherstack; Redis errors logged, not fatal |
| Weatherstack API | Real-time weather data | Returns `null` gracefully; greeting still works without weather |
| Apache Kafka | Sentiment digest pipeline | Falls back to direct `EmailService.sendemail()` |
| Gmail SMTP | Email delivery | Logged error; no retry currently implemented |

---

## 9. Deployment Overview

```
┌──────────────────────────────────────────────────────┐
│  Developer machine / CI                              │
│  mvnw package  →  journalApp-0.0.1-SNAPSHOT.jar      │
└───────────────────────────┬──────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────┐
│  Runtime Environment                                 │
│                                                      │
│  ENV VARS:  MONGODB_URI  JWT_SECRET                  │
│             REDIS_URI    WEATHER_API_KEY              │
│             MAIL_*       KAFKA_*                     │
│                                                      │
│  java -jar journalApp.jar                            │
│  → listens on :8081/journal                          │
└──────────────────────────────────────────────────────┘
```

For local development, the same env vars are loaded from the gitignored `.env` file via `spring.config.import`.
