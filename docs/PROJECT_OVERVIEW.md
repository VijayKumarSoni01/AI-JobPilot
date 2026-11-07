# Project Overview - AI JobPilot

## 1. Introduction

AI JobPilot is an AI-powered job search and career management platform. It aims to help job seekers organize applications, improve application materials, prepare for interviews, and understand their job-search performance.

The project is being developed incrementally, starting with a secure backend foundation.

## 2. Product Goals

- Centralize job application tracking.
- Help users tailor resumes and cover letters to job descriptions.
- Provide structured interview preparation.
- Provide job-search analytics and insights.
- Allow users to review and approve applications before external submission.

## 3. Planned Product Modules

| Module | Description | Status |
|---|---|---|
| Authentication | Signup, login, JWT security, exception handling & tests | Completed |
| Job tracking | Manage companies, roles, application status and dates | In Progress / Next |
| Resume management | Store and manage resumes | Planned |
| AI resume tailoring | Tailor content to job descriptions | Planned |
| Cover letters | Generate customized cover letters | Planned |
| Interview preparation | Question bank and mock interviews | Planned |
| Analytics | Application and interview statistics | Planned |
| Frontend | User dashboard and workflows | Planned |

## 4. Technology Stack

### Backend
- Java 21
- Spring Boot 4.0.9
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JJWT 0.12.5
- MySQL 8
- Flyway
- Maven
- Lombok
- Actuator

### Frontend (planned)
- React
- Vite
- TypeScript
- Tailwind CSS

### Development Tools
- Git and GitHub
- VS Code
- MySQL installed locally
- Docker where needed for future supporting services

## 5. Architecture

The application follows a modular monolith architecture. The backend is one deployable Spring Boot application with layered components.

Microservices are not part of the current architecture.

## 6. Current Scope

The current implementation includes the backend foundation, Flyway V1 migration (`users`), complete JWT authentication module with signup/login REST APIs, global exception handling, account status enforcement, and automated unit test suite (`BUILD SUCCESS`).

Job tracking, AI functionality, analytics, and the frontend remain upcoming/future work.

See [Implementation Status](IMPLEMENTATION_STATUS.md) and [Development Roadmap](DEVELOPMENT_ROADMAP.md).
