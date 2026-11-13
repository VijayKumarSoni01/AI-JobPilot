# Product and Technical Requirements - AI JobPilot

## 1. Purpose

Define the intended functionality and technical constraints of AI JobPilot. Requirements marked as planned are not claims of completed implementation.

## 2. User Roles

### USER
- Register and sign in.
- Manage personal job-search information.
- Track owned applications.
- Manage private resumes and cover letters.
- Prepare for interviews.
- View personal analytics.
- Review and approve AI-generated content before use.

### ADMIN
- Manage authorized platform-level configuration.
- Review operational and moderation data permitted by policy.
- Perform explicitly authorized administrative operations.
- Never access private user content without an authorized purpose and audit trail.

Role assignment and authorization are owned by `auth-service`; profile data is owned by `user-service`.

## 3. Core Workflows

### Registration and authentication
1. A USER submits email and password to `auth-service` through the API Gateway.
2. The service validates input, rejects duplicate email, hashes the password, and persists the account.
3. Login verifies credentials and account status, then returns an access JWT and refresh token.
4. Logout revokes the refresh token; protected requests require a valid access JWT.

### Profile and resume workflow
1. An authenticated USER creates or updates profile data in `user-service`.
2. The USER uploads or edits private resumes through `resume-service`.
3. Resume versions remain associated with the owning user.
4. AI suggestions remain drafts until the USER reviews and approves them.

### Job discovery, filtering, matching, and applications
1. `job-service` imports or stores job listings and exposes search/filter APIs.
2. `matching-service` calculates recommendations from permitted job and resume data.
3. The USER reviews a recommended job and creates an application in `application-service`.
4. Application status changes are owned by the USER and may publish events for notifications and analytics.
5. No external application is submitted without explicit USER confirmation.

### AI and notification workflow
1. The USER requests resume tailoring, cover-letter generation, or interview preparation.
2. `ai-service` creates a trackable generation request and returns a draft/result.
3. The USER can accept, edit, or reject the generated content.
4. `notification-service` sends only authorized notifications and records delivery status.

## 4. Functional Requirements

### FR-01: Authentication
- Users can register using an email and password.
- Users can log in using valid credentials.
- Passwords must be stored as secure hashes.
- Authenticated requests must use JWT authentication.
- Duplicate email registration must be rejected.
- Account status must be enforced during authentication.

Status: Implemented in `backend/auth-service`; signup, login, BCrypt, JWT validation, refresh rotation, revocation, logout, account-status checks, and automated tests are present.

### FR-02: Job Application Tracking
- Users can create job application records.
- Users can view their own applications.
- Users can update application details and status.
- Users can delete their own records.
- Users must not access another user's records.

Status: Implemented in the former monolith baseline but not yet extracted into `backend/application-service`; migration pending.

### FR-03: Resume Management
- Users can upload or create resume records.
- Users can manage multiple resume versions.
- Resume data must be associated with its owner.

Status: Planned.

### FR-04: AI Resume Tailoring
- Accept a resume and job description.
- Generate tailored resume suggestions.
- Allow users to review generated content.
- Preserve user control over final content.

Status: Planned.

### FR-05: Cover Letters
- Generate job-specific cover letter drafts.
- Allow users to review and edit drafts.

Status: Planned.

### FR-06: Interview Preparation
- Generate role-specific interview questions.
- Provide structured mock interview workflows.
- Track practice progress.

Status: Planned.

### FR-07: Analytics
- Summarize application statuses.
- Track interview conversion and job-search progress.
- Present personal insights.

Status: Planned.

### FR-08: User Approval
- Require user review and approval before any future external job application submission.
- Do not silently submit applications on behalf of users.

Status: Product requirement; automation is not implemented.

## 5. Non-Functional Requirements

### Security
- Use BCrypt for password hashing.
- Keep credentials and secrets outside source control.
- Validate and authenticate JWTs.
- Enforce ownership checks for user-specific resources.
- Apply role authorization where needed.
- Avoid exposing sensitive information in logs and API responses.

### Reliability
- Use Flyway migrations for database changes.
- Preserve referential integrity.
- Handle failures consistently.
- Add integration tests for critical workflows.

### Maintainability
- Follow the accepted microservices architecture with clear service and data ownership boundaries.
- Within each service, keep controllers, services, repositories, DTOs, and entities separated where those layers are used.
- Prefer constructor injection.
- Avoid unnecessary dependencies and abstractions.

### Performance
- Use appropriate database indexes.
- Paginate potentially large result sets.
- Optimize expensive operations when measured.

### Usability
- Provide clear validation errors.
- Keep users informed about application and AI workflow status.
- Allow users to review generated content.

## 6. Technical Constraints

- Java 21.
- Spring Boot 4.0.9.
- Maven.
- MySQL 8.
- Flyway-managed schema.
- React + Vite + TypeScript + Tailwind CSS planned for the frontend.
- Microservices architecture with an API Gateway, independently deployable services, database ownership per service, and REST/Kafka communication.

## 7. Acceptance Principles

A requirement is complete only when:
1. Its implementation exists.
2. Its expected behavior is verified.
3. Relevant automated tests pass.
4. Documentation reflects its actual behavior.
