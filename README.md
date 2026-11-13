# AI-JobPilot

AI-JobPilot is an AI-powered job search and career management platform being built as Java microservices. The active implementations are the Auth Service and API Gateway. Other service projects are buildable foundations only; their domain features will be implemented in later roadmap phases.

## Repository layout

```text
frontend/                 Planned React + Vite + TypeScript frontend
backend/
  api-gateway/            Public API entry point
  auth-service/            Authentication and token lifecycle
  service-discovery/       Service discovery project foundation
  config-server/           Central configuration project foundation
  user-service/             Profile project foundation
  resume-service/           Resume project foundation
  job-service/              Job catalog project foundation
  matching-service/         Matching project foundation
  ai-service/               AI orchestration project foundation
  application-service/      Application tracking project foundation
  notification-service/     Notification project foundation
infrastructure/             Local infrastructure configuration
docs/                       Requirements, architecture, database and event docs
scripts/                    Windows development and test scripts
```

## Technology

- Java 21
- Maven Wrapper in each backend project
- Spring Boot 4.0.9
- Spring Cloud Gateway
- MySQL and Flyway for Auth Service
- Spring Security, BCrypt, and JJWT in Auth Service
- REST for synchronous APIs; Kafka is planned for asynchronous events
- React, Vite, TypeScript, and Tailwind CSS are planned for the frontend

## Local configuration

Copy `.env.example` to `.env` as a reference, then set the variables in your shell or IDE run configuration. Spring Boot does not automatically load a plain `.env` file.

The Auth Service currently expects a MySQL database reachable using `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`. Create the local development database before starting it. Never place real secrets in `.env.example` or commit `.env`.

The Auth Service defaults to port `8081`. The API Gateway defaults to port `8088`. Other setup projects expose configurable default ports in their respective `application.yml` files.

## Build and test

Run the test suite for all backend projects:

```powershell
.\scripts\test-all.ps1
```

Run one service independently:

```powershell
cd backend\auth-service
.\mvnw.cmd clean test
```

Every backend project has an independent `pom.xml` and Maven Wrapper. New services start from the tested [service template](docs/architecture/SERVICE_TEMPLATE.md), which uses the shared build parent for common Java and Spring Boot versions. Some projects currently contain only a Spring Boot context-loading foundation, not completed business functionality.

## Start core services

Set the required environment variables for Auth Service and Gateway, then run each in a separate terminal:

```powershell
cd backend\auth-service
.\mvnw.cmd spring-boot:run
```

```powershell
cd backend\api-gateway
.\mvnw.cmd spring-boot:run
```

Gateway routes and security configuration are in `backend/api-gateway/src/main/resources/application.yml`. Service discovery and centralized configuration integration are future roadmap work.

## Documentation

- [Requirements](docs/requirements.md)
- [Implementation status — authoritative tracker](docs/IMPLEMENTATION_STATUS.md)
- [Development roadmap](docs/DEVELOPMENT_ROADMAP.md)
- [Architecture documentation](docs/architecture/)
- [Database ownership](docs/database/OWNERSHIP.md)
- [Kafka event catalogue](docs/events/CATALOGUE.md)
- [Agent instructions](AGENTS.md)

## Development workflow

Use `main` for stable work and create `feature/<short-description>` branches for changes. Merge through reviewed pull requests. Do not commit secrets, build output, or dependencies.
