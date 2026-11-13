# Kafka Event Catalogue

Events use versioned names and immutable envelopes containing `eventId`, `eventType`, `eventVersion`, `occurredAt`, `producer`, `correlationId`, and `payload`.

| Event | Producer | Consumers |
|---|---|---|
| UserRegistered.v1 | auth-service | user-service, application-service, notification-service |
| ResumeUpdated.v1 | resume-service | matching-service, ai-service |
| JobUpdated.v1 | job-service | matching-service, application-service |
| ApplicationStatusChanged.v1 | application-service | notification-service |
| AiJobCompleted.v1 | ai-service | notification-service |
