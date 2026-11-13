# API and Event Inventory

## REST API inventory

Local API Gateway base URL: `http://localhost:8088`. Auth Service direct base URL: `http://localhost:8081`. The Gateway currently forwards auth paths to Auth Service.

| Method | Path | Auth | Request | Response | Status |
|---|---|---|---|---|---|
| POST | `/api/auth/signup` | Public | `SignupRequest` | `AuthResponse` | Implemented |
| POST | `/api/auth/login` | Public | `LoginRequest` | `AuthResponse` | Implemented |
| POST | `/api/auth/refresh` | Public | `RefreshTokenRequest` | `AuthResponse` | Implemented |
| POST | `/api/auth/logout` | Public | `RefreshTokenRequest` | `204 No Content` | Implemented |
| GET | `/actuator/health` | Public | — | Health payload | Implemented by Gateway and service Actuator endpoints |

No job application or other business-service API is currently implemented. A configured Gateway route is not evidence that its target service has a working API.

## Domain/event inventory

There is currently no event broker, publisher, or consumer in the repository. The following are planned contracts, not implemented events:

| Event | Producer | Consumers | Status |
|---|---|---|---|
| `UserRegistered.v1` | Auth Service | Future User and Notification services | Planned |
| `ResumeUpdated.v1` | Future Resume Service | Future Matching and AI services | Planned |
| `JobUpdated.v1` | Future Job Service | Future Matching and Application services | Planned |
| `ApplicationStatusChanged.v1` | Future Application Service | Future Notification service | Planned |
| `AiJobCompleted.v1` | Future AI Service | Future Notification service | Planned |

Future event contracts should define an immutable, versioned envelope containing an event ID, event type/version, occurrence time, producer, correlation ID, and payload. Evaluate an outbox where reliable publication is required; see ADR-007.
