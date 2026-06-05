# Journal App

A REST API for a personal journaling app. Built while learning Spring Boot — covers auth, databases, caching, messaging, and scheduling.

## What it does

- Users can sign up, log in, and manage their journal entries
- Entries get a sentiment score processed asynchronously via Kafka
- Weekly sentiment summary is emailed to users via a scheduler
- Weather info is shown on the greeting endpoint (cached in Redis)
- JWT-based authentication for all protected routes

## Stack

- **Spring Boot 2.7** — core framework
- **MongoDB** — stores users and journal entries
- **Redis** — caches weather API responses
- **Kafka** — async sentiment analysis pipeline
- **JWT** — stateless auth
- **Swagger** — auto-generated API docs (`/swagger-ui/index.html`)

## Running locally

Requires MongoDB and Redis on default ports (`27017`, `6379`).

```bash
cd journalApp
./mvnw spring-boot:run
```

To use real cloud services, set these env vars before running:

```
MONGODB_URI        mongodb+srv://...
REDIS_URI          redis://...
JWT_SECRET         a-long-random-secret
WEATHER_API_KEY    from weatherstack.com
MAIL_USERNAME      your-gmail@gmail.com
MAIL_PASSWORD      your-gmail-app-password
KAFKA_BOOTSTRAP_SERVERS  your-kafka-host:9092
KAFKA_USERNAME     ...
KAFKA_PASSWORD     ...
```

## Endpoints

**Public (no auth needed)**
```
GET  /public/health-check
POST /public/signup
POST /public/login          → returns JWT token
```

**User (Bearer token required)**
```
GET    /user/new            greeting + today's weather
PUT    /user                update your username/password
DELETE /user                delete your account
```

**Journal (Bearer token required)**
```
GET    /journal             list all your entries
POST   /journal             create entry
GET    /journal/id/{id}     get one entry
PUT    /journal/id/{id}     update entry
DELETE /journal/id/{id}     delete entry
```

**Admin**
```
GET  /admin                 list all users (paginated)
POST /admin/create-admin-user
```

## Known issues / TODO

- Test class names are all lowercase (`userservicetest`) so Maven Surefire skips them — need to rename to `UserServiceTest` etc.
- Old credentials were committed in an earlier commit and are still in git history — those need to be rotated even though `application.yml` no longer has them hardcoded
- Frontend (`frontend/`) is a static HTML/JS prototype, not wired to a build tool yet
