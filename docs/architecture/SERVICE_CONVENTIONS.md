# Common Service Conventions

These conventions apply to every newly implemented AI-JobPilot service. [`backend/service-template`](../../backend/service-template/) demonstrates them.

## Logging

- Use Spring Boot's default SLF4J/Logback integration; do not add a second logging framework without a documented reason.
- Preserve or create an `X-Correlation-Id` for every HTTP request, return it in the response, and include it in log output through MDC.
- Log operational context such as request path and failure category, but never log passwords, JWTs, refresh tokens, database credentials, or private resume content.
- Log unexpected failures once at the exception boundary with stack traces; return a safe generic client message.

## Validation and API errors

- Put Bean Validation annotations on request DTOs and use `@Valid` at API boundaries.
- Reject malformed client input with HTTP `400` and field-level validation details.
- Use `@RestControllerAdvice` for a consistent error response containing timestamp, status, safe message, path, and optional field errors.
- Map known domain failures to intentional HTTP status codes. Do not expose implementation details or exception stack traces to clients.

## Naming and layering

- Service artifact/directory names use kebab case: `<domain>-service`.
- Java package roots use lowercase: `com.aijobpilot.<domain>service`.
- Keep controllers in `api`, cross-cutting filters/configuration in `config`, API error mapping in `exception`, and business logic in `service`.
- Each service keeps its own configuration, Maven Wrapper, tests, and schema migrations.

## Testing

- A service must pass `./mvnw.cmd clean test` independently.
- Add focused MVC tests for validation and errors, plus unit/integration tests for meaningful business behavior as it is implemented.
- The repository CI workflow runs all independently buildable backend projects.
