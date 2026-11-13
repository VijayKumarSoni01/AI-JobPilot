# AI-JobPilot — Authoritative Agent Instructions

This is the single source of truth for AI agents working in this repository. Tool-specific pointers must refer here rather than duplicate project rules.

## Project

AI-JobPilot is an AI-powered job search and career management platform. It is transitioning to independently runnable Java 21 / Spring Boot 4.0.9 microservices, fronted by Spring Cloud Gateway. The existing monolith has been removed. The active implementations are `backend/auth-service` and `backend/api-gateway`; other services may be setup-only shells until their roadmap phase.

## Stack and architecture

- Java 21, Maven Wrapper, Spring Boot 4.0.9, Spring Cloud where required.
- MySQL with Flyway for service-owned schemas and migrations.
- Spring Security, BCrypt and JJWT in Auth Service.
- REST for synchronous service calls; Kafka for planned versioned domain events.
- React, Vite, TypeScript and Tailwind are the planned frontend stack.
- Each service owns its data. Services must not query another service's database.
- Do not claim a scaffold or planned feature is a completed business service.

## Mandatory context

Before making changes, read this file, `docs/IMPLEMENTATION_STATUS.md`, `docs/PROJECT_OVERVIEW.md`, the relevant documents under `docs/architecture/`, and service-specific source/tests.

## Coding and security

- Preserve existing user changes; inspect `git status --short` before editing.
- Follow service-local package, naming, configuration, and test conventions.
- Prefer constructor injection, DTOs at API boundaries, focused controllers, and service-layer business logic.
- Use Flyway for schema changes; do not casually edit applied migrations.
- Keep secrets out of source control. `.env.example` contains placeholders only; `.env` is local.
- Signup must never accept a client-selected privileged role.
- Validate JWT signatures/expiry and enforce ownership for user-owned resources.
- Never weaken authentication/authorization to bypass tests.
- Handle errors explicitly; do not return success-shaped fallbacks or expose stack traces/secrets.
- Avoid unnecessary dependencies and unrelated refactors.

## Testing

- Add or update tests for meaningful behavior.
- Run each affected service's Maven Wrapper tests. Use `scripts/test-all.ps1` for the whole current backend.
- Never report tests as passing unless actually run; describe environmental blockers accurately.

## Git and collaboration

- Do not commit, push, amend, reset, clean, or discard changes without explicit user permission.
- Keep `docs/IMPLEMENTATION_STATUS.md` as the authoritative progress tracker. `docs/DEVELOPMENT_ROADMAP.md` is the plan, not a second status tracker.
- Use `main` for stable work and `feature/<short-description>` branches for changes; integrate through reviewed pull requests.
- Report changed files, actual verification results, and remaining work.

## Documentation

- `docs/requirements.md`: product requirements and workflows.
- `docs/IMPLEMENTATION_STATUS.md`: verified current status.
- `docs/DEVELOPMENT_ROADMAP.md`: planned sequence.
- `docs/architecture/`: service boundaries, APIs/events, conventions, diagrams and ADRs.
- `docs/database/`: database ownership and design.
- `docs/events/`: event catalogue and contracts.
