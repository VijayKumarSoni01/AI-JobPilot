# Flyway Migration Conventions and Database Standards

## 1. Schema Ownership
- Each microservice owns its database schema and its Flyway migration scripts.
- Migrations reside under `src/main/resources/db/migration/`.
- No service is permitted to apply migrations to or query another service's database.

## 2. Naming Conventions
Flyway migration files must follow the strict versioned naming format:
```text
V{version}__{description}.sql
```
- `V`: Prefix indicating a versioned migration.
- `{version}`: Sequential integer or dot-separated version (e.g., `1`, `2`, `1_1`).
- `__`: Two underscores separating version and description.
- `{description}`: Snake_case summary of the schema change (e.g., `create_users_table`).
- `.sql`: File extension.

Example:
`V1__create_users_table.sql`
`V2__create_refresh_tokens_table.sql`

## 3. Rules for Changes
- **Never modify an already applied migration script.** Once a migration has been committed and run in development or production, changing its contents will cause Flyway checksum validation errors.
- Always create a new versioned migration (`V3__...`) for any subsequent schema changes (adding columns, indices, tables).
- Set Hibernate ddl-auto mode to `validate` (`spring.jpa.hibernate.ddl-auto=validate`) in all service configurations to ensure JPA entities match Flyway-managed database schemas exactly.

## 4. Indexing and Relationships
- Foreign keys must be explicitly named (`CONSTRAINT fk_{table}_{ref_table} FOREIGN KEY (...) REFERENCES ...`).
- Unique constraints must be named (`CONSTRAINT uk_{table}_{column} UNIQUE (...)`).
- Indexes should be added on columns frequently used in WHERE clauses, foreign keys, or unique lookups.
