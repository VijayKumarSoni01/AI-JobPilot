# Architectural Decision Records - AI JobPilot

## ADR 1: Java 21 and Spring Boot 4.0.9

- Status: Accepted
- Decision: Use Java 21 and Spring Boot 4.0.9.
- Reason: Modern Java language/runtime features and the Spring ecosystem.
- Consequence: Code and dependencies must remain compatible with the selected versions.

## ADR 2: Modular Monolith

- Status: Accepted
- Decision: Build one Spring Boot application with separated layers and feature modules.
- Reason: Keep initial development and deployment manageable while preserving clear boundaries.
- Consequence: Do not introduce microservices without an explicit architectural review and approval.

## ADR 3: MySQL and Flyway

- Status: Accepted
- Decision: Use MySQL as the relational database and Flyway for versioned SQL migrations.
- Hibernate schema mode is `validate`.
- Consequence: Schema changes must be represented by new migration scripts.

## ADR 4: Environment-Based Secret Configuration

- Status: Accepted
- Decision: Keep sensitive credentials outside source control and inject them through environment variables.
- Current common configuration reads `JWT_SECRET` and `JWT_EXPIRATION_TIME`.
- The development datasource currently reads `DB_PASSWORD`.
- The development datasource URL and username are currently defined in `application-dev.yml`.
- Consequence: Configuration should be made environment-specific before production deployment. Do not assume all datasource properties are currently environment-driven.

## ADR 5: Stateless JWT Authentication

- Status: Accepted
- Decision: Use Spring Security stateless sessions and JJWT 0.12.5.
- A custom `JwtAuthenticationFilter` extracts and validates bearer tokens.
- BCrypt is used for password hashing.
- Consequence: Protected API requests must provide valid bearer tokens. Token lifecycle, revocation, and refresh-token behavior require future design.

## ADR 6: React and Vite Frontend

- Status: Accepted
- Decision: Use React, Vite, TypeScript, and Tailwind CSS for the planned frontend.
- Consequence: Do not replace the planned stack with Next.js, Vue, or another framework without approval.
