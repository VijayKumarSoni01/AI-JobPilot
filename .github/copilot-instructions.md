# GitHub Copilot Instructions — AI JobPilot

## Mandatory Project Context

Before making changes, read:

- `/AGENTS.md`
- `/docs/IMPLEMENTATION_STATUS.md`
- `/docs/PROJECT_OVERVIEW.md`
- `/docs/architecture.md`

`AGENTS.md` is the authoritative source of project-wide instructions.

## Copilot Working Rules

1. Inspect existing implementation before proposing code changes.
2. Preserve existing user changes and uncommitted work.
3. Follow Java 21 and Spring Boot 4.0.9.
4. Maintain the modular monolith architecture.
5. Use Flyway for database schema changes.
6. Follow existing JWT and Spring Security conventions.
7. Use React + Vite + TypeScript + Tailwind CSS for the planned frontend.
8. Avoid unnecessary dependencies, abstractions, and rewrites.
9. Add or update tests for meaningful behavior changes.
10. Never claim tests passed unless they were actually executed.
11. Never commit, push, or discard changes without explicit permission.

## Project-Specific Restrictions

- Do not introduce microservices without approval.
- Do not replace React + Vite with Next.js or Vue.
- Do not upgrade Java or Spring Boot without approval.
- Do not weaken authentication or authorization to bypass errors.
- Never hardcode secrets or credentials.
- Never modify applied Flyway migrations casually.
- Do not assume MySQL runs in Docker; current development uses local MySQL.

## Implementation Approach

For substantial tasks:

1. Explain the intended implementation briefly.
2. Identify files that need modification.
3. Make focused changes.
4. Verify implementation and tests.
5. Report modified files, test results, and remaining work.

Keep explanations clear and practical because the project owner is learning Java backend development.
