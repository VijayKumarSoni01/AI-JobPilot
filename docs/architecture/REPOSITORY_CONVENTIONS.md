# Repository and Development Conventions

## Service projects

- Each directory directly under `backend/` that represents a service has its own `pom.xml`, Spring Boot application, configuration, tests, and Maven Wrapper.
- New services start from the tested [service template](SERVICE_TEMPLATE.md) and inherit dependency versions from `build/service-parent`.
- Use Java 21 and Spring Boot 4.0.9 unless a documented dependency compatibility decision requires otherwise.
- Use the Maven Wrapper from the service directory: `.\mvnw.cmd clean test`.
- Service scaffolds are setup foundations only; do not imply their domain behavior is complete before implementation and tests exist.
- Keep each service's package root under `com.aijobpilot.<service>`.

## Branch strategy

- `main` contains reviewed, stable work.
- Create short-lived branches named `feature/<short-description>`, `fix/<short-description>`, or `docs/<short-description>`.
- Keep pull requests focused and run the affected service tests before requesting review.
- Do not commit credentials, `.env`, Maven `target/`, or frontend `node_modules/`.

## Microservice boundaries

- A service owns its schema, migrations, and domain data.
- Services communicate over documented REST APIs or versioned Kafka events.
- Do not read another service's database directly.
- The API Gateway is the public client entry point; services must not trust caller-supplied identity headers.
- Keep contracts and event schemas backward-compatible or version them explicitly.

## Code and tests

- Use constructor injection and validate API input.
- Keep HTTP concerns in controllers, business rules in services, and persistence queries in repositories.
- Use Flyway for relational schema changes and test important migrations.
- Add unit or integration tests for meaningful behavior; test authorization and user ownership explicitly.

## Configuration and secrets

- `.env.example` contains placeholder values only.
- Local `.env` is ignored and is not automatically loaded by Spring Boot.
- Supply secrets and runtime configuration through environment variables or deployment secret management.
