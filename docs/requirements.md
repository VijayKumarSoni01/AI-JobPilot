# Product and Technical Requirements - AI JobPilot

## 1. Purpose

Define the intended functionality and technical constraints of AI JobPilot. Requirements marked as planned are not claims of completed implementation.

## 2. User Roles

### Job Seeker
- Register and sign in.
- Manage personal job-search information.
- Track applications.
- Manage resumes and cover letters.
- Prepare for interviews.
- View personal analytics.

### Administrator (future)
- Manage platform-level configuration.
- Perform authorized administrative operations.

## 3. Functional Requirements

### FR-01: Authentication
- Users can register using an email and password.
- Users can log in using valid credentials.
- Passwords must be stored as secure hashes.
- Authenticated requests must use JWT authentication.
- Duplicate email registration must be rejected.
- Account status must be enforced during authentication.

Status: Basic signup, login, BCrypt, and JWT implementation present. Additional security behavior and tests remain necessary.

### FR-02: Job Application Tracking
- Users can create job application records.
- Users can view their own applications.
- Users can update application details and status.
- Users can delete their own records.
- Users must not access another user's records.

Status: Planned.

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

## 4. Non-Functional Requirements

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
- Follow modular monolith architecture.
- Keep controllers, services, repositories, DTOs, and entities separated.
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

## 5. Technical Constraints

- Java 21.
- Spring Boot 4.0.9.
- Maven.
- MySQL 8.
- Flyway-managed schema.
- React + Vite + TypeScript + Tailwind CSS planned for the frontend.
- Modular monolith; microservices are not part of the current design.

## 6. Acceptance Principles

A requirement is complete only when:
1. Its implementation exists.
2. Its expected behavior is verified.
3. Relevant automated tests pass.
4. Documentation reflects its actual behavior.
