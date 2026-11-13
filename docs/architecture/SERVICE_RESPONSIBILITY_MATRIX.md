# Service Responsibility Matrix

| Service | Responsibility and owned data | Current status |
|---|---|---|
| `api-gateway` | Public entry point, routing, JWT validation, CORS, trusted identity propagation | Gateway foundation implemented; discovery integration is future work |
| `auth-service` | Credentials, roles, account status, access and refresh token lifecycle | Implemented and tested |
| `user-service` | Profiles, skills, education, experience, preferences | Spring Boot project shell only |
| `resume-service` | Resume files/metadata and versions | Spring Boot project shell only |
| `job-service` | Job listings, companies, discovery and filters | Spring Boot project shell only |
| `matching-service` | Candidate/job match results and recommendations | Spring Boot project shell only |
| `ai-service` | AI generation requests, provider integration and usage | Spring Boot project shell only |
| `application-service` | User-owned job application lifecycle | Spring Boot project shell only; former monolith implementation was removed |
| `notification-service` | Delivery requests and notification state | Spring Boot project shell only |
| `service-discovery` | Service registration and discovery | Spring Boot project shell only; Eureka not yet configured |
| `config-server` | Centralized non-secret runtime configuration | Spring Boot project shell only; Spring Cloud Config not yet configured |

Each business service owns its schema and migrations. Services must not query another service's database directly. Synchronous interactions use documented REST APIs; asynchronous interactions use versioned Kafka events. Planned events are not evidence of an active broker or implemented consumers.
