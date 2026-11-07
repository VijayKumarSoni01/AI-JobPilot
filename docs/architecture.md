# Architecture - AI JobPilot

## 1. Architectural Style

AI JobPilot uses a **modular monolith**.

The backend is one Spring Boot application and one deployable unit. Code is organized into layers and feature areas. The architecture does not currently use microservices.

## 2. High-Level Architecture

```text
React + Vite Frontend (planned)
             |
          REST API
             |
Spring Boot Application
  |          |          |          |
Controller  Security   Actuator   GlobalExceptionHandler
  |
Service
  |
Repository
  |
JPA / Hibernate
  |
MySQL Database
  ^
  |
Flyway Migrations
```

The frontend is planned and is not yet implemented.

## 3. Backend Technology

- Java 21
- Spring Boot 4.0.9
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JJWT 0.12.5
- MySQL 8
- Flyway
- Maven

## 4. Package Structure

Base package: `com.aijobpilot.backend`

| Package | Responsibility |
|---|---|
| `config` | Application and security configuration |
| `controller` | REST API endpoints |
| `dto` | Request and response objects |
| `entity` | JPA entities |
| `exception` | Custom exceptions and `GlobalExceptionHandler` |
| `repository` | Database access |
| `security` | JWT and user authentication components |
| `service` | Business logic |

The actual source tree is authoritative if this structure changes.

## 5. Authentication Flow

### Signup
1. Client sends signup data to `POST /api/auth/signup`.
2. Controller validates request payload using Jakarta Validation (`SignupRequest`).
3. AuthService checks whether the email already exists (`DuplicateEmailException`).
4. Password is encrypted using BCrypt (`PasswordEncoder`).
5. User is saved in MySQL (`users` table).
6. JWT token is generated and returned in `AuthResponse`.

### Login
1. Client sends credentials to `POST /api/auth/login`.
2. AuthService verifies user existence and account status (`ACTIVE` via `AccountInactiveException`).
3. AuthenticationManager authenticates the credentials.
4. JWT token is generated and returned in `AuthResponse`.

### Protected Requests
1. Client sends `Authorization: Bearer <token>`.
2. `JwtAuthenticationFilter` extracts and validates the token.
3. User details are loaded via `CustomUserDetailsService`.
4. Valid authentication is established in the Spring Security context.
5. Authorization rules determine whether the request proceeds.

## 6. Security Configuration

- Stateless HTTP sessions (`SessionCreationPolicy.STATELESS`).
- CSRF disabled for stateless REST API.
- BCrypt password encoder.
- JWT authentication filter added before `UsernamePasswordAuthenticationFilter`.
- Public endpoints: `/api/auth/**`, `/actuator/health/**`.
- Other routes require authentication.

## 7. Database Architecture

- MySQL is the relational database.
- Flyway manages schema migrations.
- Hibernate uses `ddl-auto: validate`.
- V1 creates the `users` table (`id`, `email`, `password_hash`, `role`, `status`, `created_at`, `updated_at`).

See [Database Design](DATABASE_DESIGN.md).

## 8. Configuration Profiles

- `application.yml`: core application properties, datasource placeholders, and JWT configuration.
- `application-dev.yml`: development profile configuration (SQL query logging).

Environment variables used: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `JWT_EXPIRATION_TIME`.

## 9. Future Architecture Work

- Job application domain module.
- Resume management module.
- AI integration boundary.
- React frontend integration.
