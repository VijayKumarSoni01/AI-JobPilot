# Database Ownership

Each business service owns one schema/database and its migrations. No service reads another service database directly.

| Database | Owner |
|---|---|
| auth_db | auth-service |
| user_db | user-service |
| resume_db | resume-service |
| job_db | job-service |
| matching_db | matching-service |
| ai_db | ai-service |
| application_db | application-service |
| notification_db | notification-service |

Local MySQL provisioning belongs under `infrastructure/mysql`. Credentials and URLs must be supplied through environment variables.
