# AI-JobPilot Microservices Architecture

The project is organized as independently runnable services. See the service responsibility matrix, database ownership, and event catalogue for boundaries.

## Service projects

See the [service responsibility matrix](SERVICE_RESPONSIBILITY_MATRIX.md) and [local ports](LOCAL_PORTS.md). REST is intended for synchronous calls; Kafka is planned for versioned domain events. Each service owns its data and migrations.

## Architecture deliverables

- [Service responsibilities](SERVICE_RESPONSIBILITIES.md)
- [Detailed responsibility matrix](SERVICE_RESPONSIBILITY_MATRIX.md)
- [API and event inventory](API_EVENT_INVENTORY.md)
- [Database ownership](../database/OWNERSHIP.md)
- [Kafka event catalogue](../events/CATALOGUE.md)
- [Repository conventions and branch strategy](REPOSITORY_CONVENTIONS.md)
- [Spring Boot service template](SERVICE_TEMPLATE.md)
- [Common service conventions](SERVICE_CONVENTIONS.md)
- [REST, event, and consistency guidelines](COMMUNICATION_GUIDELINES.md)
- [Local ports and runtime configuration](LOCAL_PORTS.md)
- [System context diagram](diagrams/SYSTEM_CONTEXT.md)
- [Authentication sequence](diagrams/AUTH_SEQUENCE.md)
- [Job application sequence](diagrams/JOB_APPLICATION_SEQUENCE.md)
- [Architecture decision records](adrs/)

These are planning and design artifacts; they do not imply that every listed service, event, or infrastructure component is functionally implemented.
