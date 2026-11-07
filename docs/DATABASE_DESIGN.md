# Database Design - AI JobPilot

## 1. Database Technology

- Database: MySQL 8
- ORM: Hibernate through Spring Data JPA
- Migration tool: Flyway
- Hibernate schema mode: `validate`

Flyway owns schema creation and modification. Applied migrations should not be edited casually; use new sequential migrations.

## 2. Current Database

Database name: `aijobpilot`

The development configuration currently connects to:

- Host: `localhost`
- Port: `3306`
- Username: `root`
- Password: supplied through `DB_PASSWORD`

The database is installed locally on Windows. It is not currently configured as a MySQL Docker service.

## 3. Implemented Schema

### V1 - users

Migration: `V1__create_users_table.sql`

| Column | Type | Constraints |
|---|---|---|
| `id` | BIGINT | Primary key, auto increment |
| `email` | VARCHAR(255) | Not null, unique |
| `password_hash` | VARCHAR(255) | Not null |
| `role` | VARCHAR(50) | Not null, default `USER` |
| `status` | VARCHAR(30) | Not null, default `ACTIVE` |
| `created_at` | TIMESTAMP | Not null, default current timestamp |
| `updated_at` | TIMESTAMP | Not null, default current timestamp, auto-updated |

Constraints:
- Primary key: `pk_users`
- Unique email: `uk_users_email`

The `User` entity maps to this table.

## 4. Implemented Schema

### V2 - job_applications

Migration: `V2__create_job_applications_table.sql`

The table stores user-owned applications with company and role details, optional description and applied date, lifecycle status, and audit timestamps. Statuses are `SAVED`, `APPLIED`, `INTERVIEW`, `OFFER`, `REJECTED`, or `WITHDRAWN`.

Design requirements are enforced through a foreign key to `users.id`, user-scoped repository queries, and indexes on owner and owner/status.

## 5. Planned Tables

The following are conceptual future tables.

### resumes (proposed future migration)

Potential fields:
- `id`
- `user_id`
- `title`
- `content` or file metadata
- `is_tailored`
- `created_at`
- `updated_at`

The final schema should account for file storage strategy, versioning, privacy, and ownership.

## 5. Migration Rules

1. Store migrations under `backend/src/main/resources/db/migration/`.
2. Use sequential names such as `V2__create_job_applications_table.sql`.
3. Do not modify an already-applied migration to introduce schema changes.
4. Keep JPA entity mappings aligned with migration definitions.
5. Add foreign keys, indexes, and constraints deliberately.
6. Verify migrations against a real MySQL database.

## 6. Current Status

- V1 users migration: implemented and validated.
- Users entity and repository: implemented.
- Job application schema: planned.
- Resume schema: planned.
