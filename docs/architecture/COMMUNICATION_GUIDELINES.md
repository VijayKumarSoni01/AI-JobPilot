# Service Communication and Data Consistency Guidelines

## Service boundaries and deployment

Each service is independently built, tested, configured, and deployed. It owns its domain model, schema, Flyway migrations, and data-access layer. A service must never query another service's database directly.

The API Gateway is the public entry point. Internal services must validate requests and must not treat a caller-supplied identity header as trusted without an authenticated gateway or service-to-service security design.

## Synchronous REST

Use REST when the caller needs an immediate answer and the operation can tolerate a direct dependency on the responding service. Define DTO-based contracts, explicit HTTP status codes, request validation, and bounded timeouts.

- Do not retry non-idempotent writes automatically unless the API defines an idempotency key.
- Retry only transient, safe failures with a bounded policy.
- Return a clear failure response rather than a success-shaped fallback.
- Propagate `X-Correlation-Id` on outgoing requests and retain it in logs.
- Keep REST clients behind a service-layer boundary rather than placing remote calls in controllers or repositories.

## Asynchronous events

Use versioned Kafka events when a producer should not wait for consumers, when multiple consumers react independently, or when eventual consistency is acceptable. Event contracts are immutable and include an event ID, event type/version, occurred-at time, producer, correlation ID, and payload.

- Consumers must be idempotent because delivery can be repeated.
- Consumers must handle unknown fields safely to support additive contract changes.
- Do not publish an event as a substitute for an immediate request/response requirement.
- Planned events are not implemented until a publisher, broker configuration, and consumer tests exist.

## Data consistency

Cross-service transactions are not used. Each service commits its own database transaction and other services converge through REST workflows or events.

For reliable domain-event publication, use the transactional-outbox pattern: write the domain change and outbox entry in one local transaction, then publish asynchronously with retry and monitoring. The outbox decision is described in [ADR-007](adrs/ADR-007-event-publication-boundary.md).

Design commands and consumers for retries, define compensating actions for failed multi-service workflows, and expose status so clients can distinguish pending, completed, and failed work.
