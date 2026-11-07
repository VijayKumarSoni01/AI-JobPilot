# Development Roadmap - AI JobPilot

## Phase 1: Backend Foundation

Status: Completed.

- Initialize Spring Boot 4.0.9 with Java 21.
- Configure Maven.
- Configure local MySQL.
- Integrate Flyway.
- Create V1 users migration.
- Configure Spring Data JPA.
- Configure Spring Security baseline.
- Add Actuator health endpoint.

## Phase 2: Authentication & Security Hardening

Status: Completed.

- Signup and login REST APIs (`/api/auth/signup`, `/api/auth/login`).
- BCrypt password hashing.
- JWT generation and validation (JJWT 0.12.5).
- Stateless Spring Security filter chain (`JwtAuthenticationFilter`).
- Account status enforcement (`ACTIVE` status check).
- Role-based authorization.
- Consistent API exception handling (`GlobalExceptionHandler`, `ErrorResponse`).
- Automated unit and service test coverage (`JwtServiceTest`, `AuthServiceTest`) verified with `BUILD SUCCESS`.

## Phase 3: Job Application Management

Status: Next (In Progress / Upcoming).

- Design V2 migration for job applications (`job_applications` table).
- Implement entity and repository.
- Implement service and DTOs.
- Implement create, read, update, and delete APIs.
- Enforce user ownership.
- Add validation and integration tests.
- Add pagination and filtering.

## Phase 4: Resume and Career Documents

Status: Planned.

- Design resume data model and storage approach.
- Implement resume CRUD.
- Support resume versions.
- Add cover letter management.

## Phase 5: AI Features

Status: Planned.

- Define AI provider integration boundary.
- Implement resume tailoring.
- Generate cover letter drafts.
- Generate interview questions.
- Add user review and approval workflows.
- Track AI usage and handle provider failures.

## Phase 6: Frontend

Status: Planned.

- Initialize React + Vite + TypeScript.
- Configure Tailwind CSS.
- Build authentication screens.
- Build dashboard.
- Build job application tracker.
- Build resume and interview interfaces.
- Integrate backend APIs.

## Phase 7: Testing and Production Readiness

Status: Planned.

- Unit and integration tests.
- Security testing.
- Database migration testing.
- API documentation.
- Logging and observability.
- Production configuration and secrets management.
- Containerization and deployment.
- CI/CD pipeline.

## Development Rule

Complete and verify each phase before marking it done. Keep the implementation status document synchronized with actual source code and test results.
