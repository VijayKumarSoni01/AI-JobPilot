# AI-JobPilot Project Overview

AI-JobPilot is an AI-powered job search and career management platform. It is being built as Java microservices with an API Gateway and a planned React frontend.

## Product goals

- Track job applications and job-search activity.
- Manage private profiles, resumes, and cover letters.
- Discover and match job opportunities.
- Provide AI-assisted resume tailoring and interview preparation.
- Notify users about relevant events.
- Require user review and approval before any external application submission.

## Technology

- Backend: Java 21, Spring Boot 4.0.9, Maven Wrapper, shared parent POM (`build/service-parent`).
- Security: Spring Security, BCrypt, JWT in Auth Service.
- Persistence: MySQL 8 (Dockerized via `docker-compose.yml` with least-privilege databases and users) and Flyway.
- Gateway: Spring Cloud Gateway.
- Synchronous communication: REST.
- Asynchronous communication: Kafka is planned.
- Frontend: React, Vite, TypeScript, Tailwind CSS (planned).

## Current implementation

- `backend/auth-service`: implemented authentication, JWT, refresh-token lifecycle, Flyway migrations, and comprehensive tests (29 tests).
- `backend/api-gateway`: implemented gateway foundation, routes, JWT validation, and token tests (4 tests).
- `backend/service-template`: reusable service template demonstrating logging, validation, and exception handling (2 tests).
- 9 additional independent service project shells (`user-service`, `resume-service`, `job-service`, `matching-service`, `ai-service`, `application-service`, `notification-service`, `service-discovery`, `config-server`) with Maven wrappers and context-load tests.
- GitHub Actions backend CI workflow (`.github/workflows/backend-ci.yml`) automatically builds and tests all services.

See [Implementation Status](IMPLEMENTATION_STATUS.md) for verified progress and [Development Roadmap](DEVELOPMENT_ROADMAP.md) for planned work.
