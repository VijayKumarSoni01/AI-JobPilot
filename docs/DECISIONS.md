# Architectural Decision Records (ADRs) - AI JobPilot

## ADR 1: Use Spring Boot 3/4 with Java 21 LTS
- **Status**: Accepted
- **Context**: Needed a robust, enterprise-grade backend framework with long-term support.
- **Decision**: Adopted Java 21 LTS and Spring Boot.
- **Consequences**: Access to modern Java features (virtual threads, pattern matching) and comprehensive enterprise ecosystem.

## ADR 2: Explicit Database Migrations via Flyway
- **Status**: Accepted
- **Context**: Database schema changes need to be version-controlled and reproducible across environments.
- **Decision**: Use Flyway for SQL-based migrations with Hibernate set to `validate`.
- **Consequences**: Strict schema control; all DB modifications go through versioned SQL scripts.

## ADR 3: Environment-Based Secret Management
- **Status**: Accepted
- **Context**: Sensitive credentials like database passwords must not be hardcoded.
- **Decision**: Use environment variables (`${DB_PASSWORD}`) in `application.yaml`.
- **Consequences**: Secure configuration management across local and production environments.
