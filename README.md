# Best Matched Restaurants - Laboratory Project

This project is maintained as a **study and experimentation lab**.
I use this repository to evolve ideas, test backend/frontend approaches, and keep my GitHub activity updated.

## Purpose

API for best-match restaurant search, with basic CRUD for restaurants and cuisines, JWT token authentication, and metrics support.

## Stack

- Kotlin + Spring Boot
- PostgreSQL
- Redis
- RabbitMQ
- Flyway
- Thymeleaf (test frontend)
- React (test frontend)

## Important Note About Frontends

This is a laboratory project.
Both frontends were created **for testing and validation purposes only**:

- Thymeleaf frontend: embedded in the Spring application
- React frontend: separate project in `react-frontend/`

## How to Run

### Option 1: Run from IDE (IntelliJ)

Run the main class `MailsonPereiraTechAssessmentApplication` (module `application`).

Backend URL: `http://localhost:8082`

> **Note:** This project depends on external PostgreSQL, RabbitMQ, and Redis services.
> If you run it via IntelliJ, make sure these services are available and set the correct environment variables:
> - PostgreSQL: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
> - Redis: `SPRING_REDIS_HOST`, `SPRING_REDIS_PORT`
> - RabbitMQ: `SPRING_RABBITMQ_HOST`, `SPRING_RABBITMQ_PORT`, `SPRING_RABBITMQ_USERNAME`, `SPRING_RABBITMQ_PASSWORD`

### Option 2: Run with Docker Compose

From the project root, run:

```bash
docker-compose up --build
```

## Default Ports

- PostgreSQL: `5432`
- Redis: `6379`
- RabbitMQ: `5672` and `15672`
- Application: `8082`


## Database and Migrations

Tables and initial data are created automatically with Flyway on the first run.

Migration directory: `classpath:db/migration`

- `V1__create_tables.sql`
- `V2__insert_data.sql`
- `V3__alter_table_set_identity_and_value.sql`

## Authentication and Roles

To access protected endpoints, generate a token using the login endpoint:

```bash
curl --location 'http://localhost:8082/v1/authentication/login' \
--header 'Content-Type: application/json' \
--data '{
  "userName": "{username}",
  "authorities": [
    "RESTAURANT_MAINTENANCE",
    "CUISINE_MAINTENANCE",
    "RESTAURANT_SEARCH",
    "METRIC_REPORT"
  ]
}'
```

Available roles:

- `RESTAURANT_MAINTENANCE`: restaurant CRUD
- `CUISINE_MAINTENANCE`: cuisine CRUD
- `RESTAURANT_SEARCH`: restaurant best-match search
- `METRIC_REPORT`: metrics reports

## 3 Ways to Test the System

### 1) Swagger

- URL: `http://localhost:8082/swagger-ui/index.html#/`
- Best for exploring contracts, parameters, and endpoint responses.

### 2) API Requests (Postman/curl)

- Postman collection: `files/postman-collection/tech-assessment.postman_collection.json`
- Main search endpoint: `http://localhost:8082/v1/restaurants/search`
- Generate the token on login and send it in the `Authorization` header (`Authorization: Bearer <token>`).

### 3) Frontend (Laboratory Testing)

- **Thymeleaf**
  - Login: `http://localhost:8082/v1/authentication/web/login`
  - Web flow to generate token and test search.
- **React**
  - Login: `http://localhost:3000/login`
  - Frontend used for UI and API integration experiments.

## Automated Tests

Service tests are available at:

- `service/src/test/kotlin/com/mailson/pereira/tech/assessment/service`

## Notes

- Current focus: technical laboratory and continuous evolution.
- Security was simplified to make local testing easier; production requires additional controls.
- This repository stays active for study, refinement, and preparation for future technical assessments.
