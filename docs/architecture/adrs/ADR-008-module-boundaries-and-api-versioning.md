# ADR-008: Microservice Boundaries and API Versioning

- Status: Accepted
- Date: 2026-11-12

## Context

AI JobPilot is organized as a microservices platform. Auth Service and API Gateway are active; other service projects may be buildable shells while their business APIs are still planned. The frontend is planned.

## Decision

Keep clear service responsibility, database ownership, and DTO-based REST boundaries. Continue the existing `/api/...` paths for current auth APIs; introduce an explicit version prefix (for example `/api/v1`) when a breaking contract or public compatibility window requires it. Do not treat scaffold-only services or configured Gateway routes as implemented features.

## Consequences

- Services can be implemented incrementally without claiming future capabilities prematurely.
- Service APIs remain independently testable and evolvable.
- Breaking changes require a documented migration plan and deprecation period.
