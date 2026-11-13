# Local Ports and Runtime Configuration

| Component | Default port | Configuration variable | Status |
|---|---:|---|---|
| API Gateway | 8088 | `GATEWAY_PORT` | Implemented |
| Auth Service | 8081 | `AUTH_SERVICE_PORT` | Implemented |
| User Service | 8082 | `USER_SERVICE_PORT` | Project shell |
| Resume Service | 8083 | `RESUME_SERVICE_PORT` | Project shell |
| Job Service | 8084 | `JOB_SERVICE_PORT` | Project shell |
| Matching Service | 8085 | `MATCHING_SERVICE_PORT` | Project shell |
| AI Service | 8086 | `AI_SERVICE_PORT` | Project shell |
| Application Service | 8087 | `APPLICATION_SERVICE_PORT` | Project shell |
| Notification Service | 8089 | `NOTIFICATION_SERVICE_PORT` | Project shell |
| Service Discovery | 8761 | `SERVICE_DISCOVERY_PORT` | Project shell |
| Config Server | 8888 | `CONFIG_SERVER_PORT` | Project shell |
| Local MySQL | 3306 | `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` | Auth Service uses it in dev profile |
| React/Vite | Not allocated | — | Planned |

Gateway routes and rate limiting use settings in `backend/api-gateway/src/main/resources/application.yml`. `.env.example` contains placeholders only. Spring Boot does not load a plain `.env` file automatically.
