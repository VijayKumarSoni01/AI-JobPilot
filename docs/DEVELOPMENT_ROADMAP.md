# Development Roadmap

This document records the intended sequence, not completion status. The verified progress tracker is [IMPLEMENTATION_STATUS.md](IMPLEMENTATION_STATUS.md).

## Completed foundation work

- Product requirements and microservice boundaries.
- Repository and service-project setup.
- Microservice fundamentals, shared service template, conventions, and backend CI.
- Auth Service foundation and authentication security.
- API Gateway authentication/routing foundation.

## Next platform milestones

1. Implement Eureka Service Discovery and register Auth Service.
2. Implement Spring Cloud Config Server and external configuration loading.
3. Integrate Gateway with discovery-based service routing and add end-to-end route tests.
4. Implement User Profile Service and its owned database.

## Business-service sequence

1. User Profile Service.
2. Resume Service and private document storage.
3. Job Service and discovery/filtering.
4. Kafka foundations and versioned event contracts.
5. Matching Service.
6. AI Service with user-review workflow.
7. Application Service with ownership and confirmation controls.
8. Notification Service.

## Client and production readiness

- Build the React + Vite + TypeScript frontend after stable APIs are available.
- Add full local stack orchestration, CI, monitoring, security/failure testing, and deployment.

Complete and test each milestone before marking it implemented.
