# AGENTS.md — AI JobPilot Project Instructions

This is the central source of truth for all AI coding agents working on AI JobPilot. Read it before making changes.

## 1. Project Identity

- Project: AI JobPilot
- Repository: https://github.com/VijayKumarSoni01/AI-JobPilot
- Local path: `D:\AI\AI-JobPilot`

AI JobPilot is an AI-powered job search and career management platform.

Product goals:
- Job application tracking.
- AI resume and cover letter tailoring.
- Interview preparation.
- Job-search analytics.
- User-reviewed application workflows.

These are product goals, not claims that every feature is implemented.

## 2. Technology Stack

### Backend
- Java 21
- Spring Boot 4.0.9
- Maven
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JJWT 0.12.5
- MySQL 8
- Flyway
- Lombok
- Spring Boot Actuator
- BCrypt password hashing

### Planned Frontend
- React
- Vite
- TypeScript
- Tailwind CSS

The frontend is not yet implemented.

### Development Environment
- Windows and PowerShell
- IntelliJ IDEA / VS Code
- Git and GitHub
- Locally installed MySQL
- Docker may be used for supporting services when needed.

Do not assume MySQL runs inside Docker. The current development configuration connects to local MySQL on port 3306.

Do not introduce another framework or replace an established technology without approval.

## 3. Architecture

The application follows a modular monolith architecture.

There is one Spring Boot backend application organized into layers and feature modules. Do not introduce microservices or distributed infrastructure unless explicitly requested.

Base package:

`com.aijobpilot.backend`

Current package conventions:

| Package | Responsibility |
|---|---|
| `config` | Application and security configuration |
| `controller` | REST API endpoints |
| `dto` | Request and response objects |
| `entity` | JPA entities |
| `repository` | Database access |
| `security` | JWT and authentication components |
| `service` | Business logic |

Follow the actual source tree when extending the application.

## 4. Mandatory Documentation

Before starting a task, read:

1. `AGENTS.md`
2. `docs/IMPLEMENTATION_STATUS.md`
3. `docs/PROJECT_OVERVIEW.md`
4. `docs/architecture.md`

Read other documentation when relevant.

Documentation responsibilities:

- `docs/IMPLEMENTATION_STATUS.md`: verified implementation and pending work.
- `docs/PROJECT_OVERVIEW.md`: product goals and technology choices.
- `docs/architecture.md`: architecture and component responsibilities.
- `docs/DATABASE_DESIGN.md`: database schema and migrations.
- `docs/DEVELOPMENT_ROADMAP.md`: implementation sequence.
- `docs/BUGS_AND_ISSUES.md`: known gaps and technical issues.
- `docs/DECISIONS.md`: architectural decisions.
- `docs/SESSION_LOG.md`: local-only working notes.
- `HANDOFF.md`: local-only continuation notes.
- `docs/project-tracker.md`: local-only learning and development tracker.

Never mark a feature complete merely because source files exist. Verify behavior and relevant tests.

Keep documentation consistent with the actual code.

## 5. Verified Project Progress

The backend currently includes:

- Spring Boot application foundation.
- MySQL connectivity.
- Flyway V1 users migration.
- User entity and repository.
- Signup and login endpoints.
- BCrypt password hashing.
- JWT generation and validation.
- Stateless Spring Security configuration.
- Actuator health endpoint.
- Application context-load test.

The current test suite has one test: `BackendApplicationTests.contextLoads`.

It does not yet verify complete authentication workflows.

The next engineering milestone is authentication hardening and automated testing, followed by Job Application CRUD.

Always inspect current source and tests before extending these features.

## 6. Coding Standards

- Use Java 21-compatible code.
- Follow Spring Boot 4.0.9 conventions.
- Prefer constructor injection.
- Keep controllers focused on HTTP handling.
- Put business logic in services.
- Keep database access in repositories.
- Use DTOs for API contracts.
- Apply Jakarta Bean Validation where appropriate.
- Use meaningful names and consistent formatting.
- Avoid unnecessary abstractions and premature optimization.
- Do not duplicate existing functionality.
- Explain why a new dependency is required.
- Handle exceptions consistently.
- Never expose stack traces, secrets, or internal implementation details in API responses.

## 7. Database and Flyway Rules

- MySQL is the primary relational database.
- Current development database: `aijobpilot`.
- Current local connection: `localhost:3306`.
- Flyway owns schema changes.
- Create a new migration for every schema change.
- Never casually modify an already-applied migration.
- Use sequential migration names, such as `V2__create_job_applications_table.sql`.
- Keep entity mappings aligned with migration definitions.
- Hibernate must use `ddl-auto: validate`.
- Never use `create`, `create-drop`, or `update` as a substitute for Flyway.
- Consider indexes, constraints, foreign keys, and user ownership.

## 8. Configuration and Secrets

Current configuration:

- `application.yml`: common configuration and JWT properties.
- `application-dev.yml`: development datasource and SQL logging.
- `DB_PASSWORD`: development database password.
- `JWT_SECRET`: JWT signing secret.
- `JWT_EXPIRATION_TIME`: JWT expiration in milliseconds.

The current development datasource URL and username are configured directly in `application-dev.yml`. `DB_URL` and `DB_USERNAME` are not currently consumed by that configuration.

Never:
- Commit `.env` files or credentials.
- Hardcode passwords, API keys, or JWT secrets.
- Log passwords, bearer tokens, or sensitive personal data.
- Ask users to paste secrets into chat.

## 9. Security Rules

- Hash passwords using BCrypt.
- Never store plaintext passwords.
- Validate JWT signatures and expiration.
- Protect user-owned resources against unauthorized access and IDOR vulnerabilities.
- Apply authentication and authorization at appropriate endpoint and service boundaries.
- Define role-based authorization when required.
- Do not make protected endpoints public to bypass authentication or tests.
- Do not weaken existing security configuration without justification and approval.

Current public routes include:
- `/api/auth/**`
- `/actuator/health/**`

Current security configuration requires authentication for other routes.

Known follow-ups:
- Enforce inactive account status.
- Add explicit role-based endpoint restrictions.
- Improve broad exception handling in `JwtAuthenticationFilter`.
- Introduce consistent API exception responses.
- Add authentication tests.

## 10. Testing and Verification

Before declaring backend work complete:

1. Inspect affected code and existing tests.
2. Add or update tests for new behavior.
3. Run relevant tests.
4. Run the complete backend test suite when practical.
5. Report actual test results.

From the project root:

```powershell
cd backend
.\mvnw.cmd clean test
```

Run the application:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

Do not claim a successful build or test unless the command completed successfully.

Explain environmental blockers accurately.

## 11. Git and Change Management

Protect existing user work.

- Inspect `git status --short` before changes.
- Preserve unrelated modifications and untracked files.
- Never run destructive Git commands.
- Never run `git reset --hard`, `git clean -fd`, or broad restore commands.
- Do not commit or push without explicit permission.
- Do not amend commits without permission.
- Do not stage unrelated changes.
- Explain significant modifications and list affected files.
- Keep local-only notes and secrets out of Git.

## 12. Multi-Agent Collaboration

All agents must follow this file.

Before coding:
- Read mandatory documentation.
- Inspect existing implementation.
- Check for work already performed.
- Avoid conflicting architectural decisions.
- Preserve changes made by other agents.

When handing off work:
1. Summarize implementation.
2. List modified and created files.
3. Report actual build and test results.
4. Identify incomplete work and known issues.
5. Update implementation status when verified progress changes.
6. Update local handoff notes when useful.
7. Report Git status.
8. Do not commit or push without permission.

## 13. Working Style

The project owner is learning Java backend development.

- Explain important decisions in practical language.
- Prefer step-by-step implementation.
- Provide exact file paths.
- Inspect existing code before implementation.
- Avoid unnecessary rewrites.
- Explain security implications and trade-offs.
- Do not silently skip requested functionality.
- Ask before major architectural or dependency changes.

## 14. Important Restrictions

Never:
- Replace the modular monolith with microservices without approval.
- Replace React + Vite with Next.js or Vue.
- Upgrade Java or Spring Boot without approval.
- Remove existing functionality to simplify implementation.
- Change applied migration history casually.
- Disable authentication or authorization to bypass errors.
- Commit, push, or discard user changes without permission.
- Invent test results or claim unverified functionality is complete.

Final rule: Preserve the existing project, follow its architecture, verify meaningful changes, and keep all AI agents aligned through this file.
