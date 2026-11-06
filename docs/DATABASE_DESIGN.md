# Database Design - AI JobPilot

## Database Engine
- **MySQL 8.0+**
- **Migration Tool**: Flyway (`V1__...`)

## Schema Design

### 1. `users` Table (Migration V1)
Stores registered user credentials and profile information.
- `id` (BIGINT, Primary Key, Auto Increment)
- `username` (VARCHAR(50), Unique, Not Null)
- `email` (VARCHAR(100), Unique, Not Null)
- `password_hash` (VARCHAR(255), Not Null)
- `created_at` (TIMESTAMP, Default CURRENT_TIMESTAMP)
- `updated_at` (TIMESTAMP, Default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)

### 2. `job_applications` Table (Upcoming V2)
Tracks job applications submitted by users.
- `id` (BIGINT, Primary Key, Auto Increment)
- `user_id` (BIGINT, Foreign Key -> users.id)
- `company_name` (VARCHAR(100), Not Null)
- `job_title` (VARCHAR(100), Not Null)
- `job_description` (TEXT)
- `status` (ENUM: APPLIED, INTERVIEWING, OFFER, REJECTED)
- `applied_date` (DATE)
- `created_at`, `updated_at`

### 3. `resumes` Table (Upcoming V3)
Stores user resumes and AI-tailored versions.
- `id` (BIGINT, Primary Key, Auto Increment)
- `user_id` (BIGINT, Foreign Key -> users.id)
- `title` (VARCHAR(100))
- `content` (TEXT)
- `is_tailored` (BOOLEAN)
- `created_at`, `updated_at`
