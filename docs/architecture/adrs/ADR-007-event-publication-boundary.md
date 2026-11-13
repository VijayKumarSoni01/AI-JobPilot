# ADR-007: Defer Event Publication and Prefer an Outbox Boundary

- Status: Proposed
- Date: 2026-11-08

## Context

The current backend is synchronous and has no broker, event bus, or asynchronous consumer. Future analytics, notifications, and AI workflows may need domain events, but publishing directly from a database transaction could lose events or create inconsistent state.

## Decision

Do not introduce an event broker in Week 1. When reliable asynchronous integration is needed, evaluate a transactional outbox in MySQL first: persist an event record in the same transaction as the aggregate change, then publish it through a separately managed dispatcher. Event payloads must be versioned and include an event ID, event type, occurred-at timestamp, aggregate ID, and user ID.

## Consequences

- Current APIs remain synchronous and have no undocumented event guarantees.
- Analytics and AI integrations can be added without coupling controllers to providers.
- The outbox adds storage and operational retry/cleanup work when adopted.
- A broker choice remains intentionally open until throughput and delivery requirements are known.
