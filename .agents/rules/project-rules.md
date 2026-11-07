---
trigger: always_on
description: AI JobPilot project architecture, coding, security, testing, and collaboration rules.
---

# AI JobPilot — Workspace Rules

## Mandatory Instructions

Follow the root `AGENTS.md` as the authoritative project instruction file.

Before making changes, read:

- `AGENTS.md`
- `docs/IMPLEMENTATION_STATUS.md`
- `docs/PROJECT_OVERVIEW.md`
- `docs/architecture.md`

## Project Architecture

- Project: AI JobPilot.
- Backend: Java 21 and Spring Boot 4.0.9.
- Architecture: Modular monolith.
- Database: MySQL with Flyway.
- Security: Spring Security, BCrypt, and JJWT 0.12.5.
- Frontend: React + Vite + TypeScript + Tailwind CSS (planned).
- Build tool: Maven.

The current development database is installed locally on Windows. Do not assume MySQL runs inside Docker.

Do not introduce microservices or replace existing technologies without approval.

## Coding Rules

- Inspect existing implementation before changing code.
- Follow existing package structure.
- Use constructor injection.
- Keep controllers, services, repositories, DTOs, and entities appropriately separated.
- Use Flyway migrations for schema changes.
- Preserve applied migration history.
- Avoid unnecessary dependencies and large rewrites.
- Protect user-owned resources with proper authorization.
- Never hardcode secrets or credentials.
- Do not claim planned features are implemented.

## Testing Rules

- Add or update tests for meaningful changes.
- Run relevant tests before reporting completion.
- Do not claim tests passed unless actually executed.
- Report environmental blockers honestly.
- Remember that the current single context-load test does not establish authentication correctness.

## Git Safety

- Preserve all existing user changes.
- Never run destructive Git commands.
- Never commit or push without explicit permission.
- Never overwrite unrelated work.

## Collaboration

- Keep implementation status accurate.
- Report files created and modified.
- Explain important design decisions.
- Identify incomplete tasks and known issues.
