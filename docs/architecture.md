# Architecture - AI JobPilot

## System Architecture
AI JobPilot follows a modular monolith architecture separated into backend REST APIs and a decoupled frontend client.

```
┌─────────────────────────┐          ┌─────────────────────────┐
│     Frontend Client     │  REST    │     Backend Server      │
│  (React / Next.js)      │ ───────> │  (Spring Boot / Java)   │
└─────────────────────────┘          └────────────┬────────────┘
                                                  │ JPA / Flyway
                                     ┌────────────▼────────────┐
                                     │      MySQL Database     │
                                     └─────────────────────────┘
```

## Backend Architecture (Spring Boot)
- **Layered Structure**:
  - `controller`: REST endpoints handling HTTP requests.
  - `service`: Business logic layer.
  - `repository`: Spring Data JPA data access interfaces.
  - `model` / `entity`: JPA domain entities.
  - `config`: Security, Flyway, and application configuration.
- **Database Migrations**: Managed explicitly via **Flyway** (`src/main/resources/db/migration/`). Hibernate `ddl-auto` is set to `validate`.

## Security Architecture
- **Authentication**: Stateless JWT (JSON Web Token) authentication.
- **Authorization**: Role-based access control (RBAC) securing user data and API endpoints using Spring Security.
