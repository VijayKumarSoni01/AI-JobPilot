# Bugs and Known Issues - AI JobPilot

Last reviewed: 2026-11-12

This document records known engineering gaps. A pending item is not necessarily a currently reproducible production bug.

## 1. Security

### SEC-01: Broad exception handling in JWT filter
- Location: `backend/auth-service/.../security/JwtAuthenticationFilter.java`
- Current behavior: Catches exceptions during token processing.
- Recommended action: Refine exception handling as security hardening proceeds.

### SEC-02: Role authorization is incomplete
- Location: `backend/auth-service/.../config/SecurityConfig.java`
- Current behavior: Basic authentication enforced; fine-grained admin roles are pending future business services.

## 2. API and Error Handling

- Auth Service uses `GlobalExceptionHandler` with domain exceptions (e.g. `DuplicateEmailException`, `InvalidCredentialsException`).
- Reusable service template demonstrates centralized exception handling and validation mapping.

## 3. Testing Status

- All 12 backend Maven projects are fully tested and passing via `scripts/test-all.ps1`.
- Total tests: 44 tests across Auth Service (29), API Gateway (4), Service Template (2), and 9 setup shells (9).

## 4. Configuration

- Database URLs and credentials are configurable via environment variables in accordance with ADR-4.
- `docker-compose.yml` provides MySQL 8 with least-privilege databases and users.

## 5. Current Test Baseline

```text
Tests run: 44
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS (across 12 projects)
```
