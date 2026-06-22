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

## Search Metrics, RabbitMQ and Reporting

This project also contains a **laboratory flow for search metrics collection**.
The idea was to validate the use of **RabbitMQ and queues** in an asynchronous scenario where restaurant searches generate events that can later be consolidated into reports.

In a hypothetical production scenario, this metrics pipeline could be used to answer questions such as:

- which search parameters are used most often;
- which parameter combinations appear more frequently;
- which searches return more restaurants;
- which searches return no matches;
- how search behavior changes by day, month, or year.

### How the metrics flow works

1. A restaurant search is executed, either directly via the search API or through the metric maintenance endpoint.
2. The application collects the search parameters that were informed and the total number of matched restaurants returned by the search.
3. This data is published asynchronously to RabbitMQ.
4. A consumer listens to the search queue and processes the metric event.
5. The consolidated data can then be queried through the reporting API.

This approach keeps the search flow focused on the user response while delegating metric processing to an asynchronous pipeline.
In the current configuration, this flow uses the RabbitMQ exchange `restaurant.search.exchange` and queue `restaurant.search.queue`, with a DLQ also configured for the laboratory scenario.

### Metric generation endpoint

For automated metric generation, the project exposes the endpoint:

- `POST /v1/metric/maintenance`

This endpoint was designed as a practical way to simulate search traffic and populate metric data through API automation tools such as Postman Runner.
It accepts the same business filters used in restaurant search, including fields such as:

- `restaurantName`
- `distance`
- `customerRating`
- `price`
- `cuisineName`
- `searchDate`

### Metric report endpoint

Metric reports are obtained through `MetricReportController`:

- `GET /v1/metric/report/average-data`

The endpoint supports period-based report generation and returns summarized information such as total searches, searches with matches, searches without matches, average numeric parameters, most used parameter keys, and most frequent parameter key/value combinations.

The Swagger documentation contains explanatory tags and descriptions for the metric endpoints, including guidance about accepted report parameters and expected period formats.

### Automation with Postman Runner and CSV files

It is possible to generate metric data through API automation.
Inside `files/metric-data/`, there are CSV files that can be used as data sources in Postman Runner or in other automation strategies.

Examples:

- `files/metric-data/planilha_1.csv`
- `files/metric-data/planilha_2.csv`
- `files/metric-data/planilha_3.csv`

These files follow the request structure expected by the metric generation flow and are useful for simulating repeated searches over different dates and filter combinations.

Suggested lab flow:

1. authenticate and obtain a JWT token;
2. create or adapt a Postman request for `POST /v1/metric/maintenance`;
3. run the request with one of the CSV files from `files/metric-data/`;
4. after the messages are processed by RabbitMQ consumers, query `GET /v1/metric/report/average-data`;
5. use Swagger tags and endpoint descriptions as a reference for the report parameters.

## Automated Tests

Service tests are available at:

- `service/src/test/kotlin/com/mailson/pereira/tech/assessment/service`

## Notes

- Current focus: technical laboratory and continuous evolution.
- Security was simplified to make local testing easier; production requires additional controls.
- This repository stays active for study, refinement, and preparation for future technical assessments.
