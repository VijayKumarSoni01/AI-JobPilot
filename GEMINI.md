# Gemini Instructions — AI JobPilot

Before performing any task in this repository:

1. Read the root `AGENTS.md` completely.
2. Read `docs/IMPLEMENTATION_STATUS.md`.
3. Read `docs/PROJECT_OVERVIEW.md`.
4. Read `docs/architecture.md`.

`AGENTS.md` is the authoritative source for architecture, technology choices, coding standards, security, testing, Git safety, and collaboration.

## Gemini-specific responsibilities

- Follow all instructions in `AGENTS.md`.
- Inspect existing code before suggesting or making changes.
- Preserve existing user modifications and uncommitted work.
- Do not independently change architecture, dependencies, or framework versions.
- Do not assume a feature is complete merely because its files exist.
- Verify implementation and tests before reporting completion.
- Explain significant changes before making them.
- Do not commit, push, or discard changes without explicit permission.

## Project identity

AI JobPilot is a Java 21, Spring Boot 4.0.9 modular monolith with MySQL, Flyway, Spring Security, and JWT.

The planned frontend is React + Vite + TypeScript + Tailwind CSS.

MySQL currently runs locally on Windows. Do not assume the database is running in Docker.

Always consult `AGENTS.md` for the complete and current project instructions.
