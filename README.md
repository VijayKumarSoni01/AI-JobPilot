# AI JobPilot

AI JobPilot is an AI-powered job search and career management platform. Its goal is to help users organize job applications, tailor resumes and cover letters, prepare for interviews, and analyze their job-search progress.

> Project status: Backend foundation and complete JWT authentication module (signup, login, filters, exception handling, and unit tests) are implemented and verified. Job management, AI features, and frontend are planned.

## Technology Stack

### Backend
- Java 21
- Spring Boot 4.0.9
- Maven
- Spring MVC
- Spring Data JPA and Hibernate
- Spring Security
- JWT using JJWT 0.12.5
- BCrypt password hashing
- MySQL 8
- Flyway
- Spring Boot Actuator

### Planned Frontend
- React
- Vite
- TypeScript
- Tailwind CSS

## Architecture

AI JobPilot uses a modular monolith architecture. The backend is one Spring Boot application organized into controllers, services, repositories, entities, DTOs, configuration, security, and exception handling components.

See [Architecture](docs/ARCHITECTURE.md).

## Current Implementation

- Spring Boot application setup
- MySQL connectivity and profiles (`application.yml` and `application-dev.yml`)
- Flyway V1 migration for users
- User entity and repository
- Signup and login endpoints with validation & error handling
- BCrypt password hashing
- JWT generation and validation (`JwtService`, `JwtAuthenticationFilter`)
- Stateless Spring Security configuration & account status enforcement
- Global Exception Handler (`GlobalExceptionHandler`) returning structured `ErrorResponse`
- Automated unit test suite (`JwtServiceTest`, `AuthServiceTest`, `BackendApplicationTests`) with `BUILD SUCCESS`
- Actuator health endpoint

See [Implementation Status](docs/IMPLEMENTATION_STATUS.md).

## Prerequisites

- JDK 21
- MySQL 8.0+
- Git

## Local Setup (Windows PowerShell)

### 1. Clone the repository

```powershell
git clone https://github.com/VijayKumarSoni01/AI-JobPilot.git
cd AI-JobPilot
```

### 2. Create the database

Start your locally installed MySQL server and execute:

```sql
CREATE DATABASE aijobpilot;
```

### 3. Configure environment variables

Set these variables in your PowerShell session:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/aijobpilot?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "your_mysql_password"
$env:JWT_SECRET = "your_secure_jwt_secret_key_minimum_256_bits_length_here_1234567890"
$env:JWT_EXPIRATION_TIME = "86400000"
```

Never commit real credentials or secrets.

### 4. Run tests

```powershell
cd backend
.\mvnw.cmd clean test
```

All unit tests (`JwtServiceTest`, `AuthServiceTest`, `BackendApplicationTests`) pass successfully with `BUILD SUCCESS`.

### 5. Run the application

```powershell
.\mvnw.cmd spring-boot:run
```

The application listens on port `8080`.

### 6. Health endpoint

```text
GET http://localhost:8080/actuator/health
```

## Authentication API

Base path: `/api/auth`

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/signup` | Register a user |
| POST | `/login` | Authenticate a user |

Both endpoints return an `AuthResponse` containing a JWT, email, role, and message.

The JWT must be sent to protected endpoints using:

```text
Authorization: Bearer <your_jwt_token>
```

## Documentation

- [Project Overview](docs/PROJECT_OVERVIEW.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Database Design](docs/DATABASE_DESIGN.md)
- [Implementation Status](docs/IMPLEMENTATION_STATUS.md)
- [Development Roadmap](docs/DEVELOPMENT_ROADMAP.md)
- [Known Issues](docs/BUGS_AND_ISSUES.md)
- [Architectural Decisions](docs/DECISIONS.md)
- [Agent Instructions](AGENTS.md)

## Development Principles

- Preserve the modular monolith architecture.
- Use Flyway for database schema changes.
- Do not edit applied migrations casually.
- Keep secrets outside source control.
- Protect user-owned resources.
- Add tests for meaningful behavior.
- Do not mark planned functionality as implemented.

## License

No license has been declared yet.
