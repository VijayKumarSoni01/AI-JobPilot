# Development Roadmap - AI JobPilot

## Phase 1: Foundation & Database Setup (Completed)
- Initialize Spring Boot project with Java 21 & Maven.
- Configure MySQL connection & Flyway migration (`V1__create_users_table.sql`).
- Set up Spring Security and Actuator health endpoints.

## Phase 2: Core Backend Services (Current)
- Implement User Authentication (Signup, Login, JWT tokens).
- Build Job Application Tracking CRUD APIs.
- Build Resume Management & Storage module.

## Phase 3: AI Integration & Features
- Integrate AI client (OpenAI / Azure OpenAI / custom LLM connector) for resume tailoring.
- Build Mock Interview Question generator.

## Phase 4: Frontend Development
- Initialize frontend application (`frontend/`).
- Build Dashboard, Application Tracker UI, and Resume Tailor interface.

## Phase 5: Testing & Deployment
- Write unit & integration tests (JUnit 5, Mockito).
- Dockerize backend and frontend (`docker-compose.yml`).
- Deploy to Cloud (Azure / AWS / Render).
