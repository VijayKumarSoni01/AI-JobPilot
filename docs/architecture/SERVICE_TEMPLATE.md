# Spring Boot Service Template

Use [`backend/service-template`](../../backend/service-template/) as the starting point for a new independently deployable business service. It is a tested reference, not an implemented business capability.

## Dependency-version decision

AI-JobPilot uses the shared Maven parent POM at [`build/service-parent/pom.xml`](../../build/service-parent/pom.xml), rather than a separate BOM. The parent establishes the Java 21 and Spring Boot 4.0.9 dependency baseline while every service retains its own Maven Wrapper, artifact, deployment lifecycle, configuration, and tests.

A new service should inherit from this parent with a relative path, then declare only the dependencies needed by that service. Service-specific integrations, such as Spring Cloud or database drivers, remain owned by the individual service POM.

## Template contents

- Java package convention: `com.aijobpilot.<service>`.
- Actuator health and info endpoints.
- Bean Validation for HTTP request DTOs.
- A centralized `@RestControllerAdvice` with a stable error shape.
- `X-Correlation-Id` request/response propagation and structured console logging through MDC.
- Focused MVC tests for both a valid request and validation failure.
- A local Maven Wrapper.

Copy the template only when creating a new service. Do not copy its `/api/template/echo` endpoint into a production service; replace it with the service's own API.

## Required service layout

```text
backend/<service-name>/
  .mvn/wrapper/
  mvnw.cmd
  pom.xml
  src/main/java/com/aijobpilot/<service>/
    api/
    config/
    exception/
    service/
  src/main/resources/application.yml
  src/test/java/com/aijobpilot/<service>/
```

The template is independently verified with:

```powershell
cd backend\service-template
.\mvnw.cmd clean test
```
