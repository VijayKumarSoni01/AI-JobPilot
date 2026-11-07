# Bugs and Known Issues - AI JobPilot

Last reviewed: 2026-11-06

This document records known engineering gaps. A pending item is not necessarily a currently reproducible production bug.

## 1. Security

### SEC-01: Broad exception handling in JWT filter
- Location: `security/JwtAuthenticationFilter.java`
- Current behavior: Catches all exceptions during token processing and silently ignores them.
- Risk: Can hide database or infrastructure errors as if they were invalid tokens.
- Recommended action: Handle expected JWT parsing/validation exceptions separately. Do not silently suppress unrelated failures.

### SEC-02: Account status is not enforced
- Location: `security/CustomUserDetailsService.java`
- Current behavior: Loads user credentials and role but does not use the `status` field to disable inactive accounts.
- Risk: A user marked inactive may still authenticate.
- Recommended action: Enforce account state through UserDetails account flags or an equivalent authentication check.

### SEC-03: Role authorization is incomplete
- Location: `config/SecurityConfig.java`
- Current behavior: Authorities are loaded, but all non-public routes currently require authentication only.
- Recommended action: Define and test role-specific endpoint restrictions when admin features are introduced.

## 2. API and Error Handling

### API-01: Generic runtime exceptions
- Location: `service/AuthService.java`
- Current behavior: Duplicate email and missing user cases use generic RuntimeException.
- Risk: Inconsistent HTTP responses and poor error classification.
- Recommended action: Introduce domain-specific exceptions and centralized exception handling.

### API-02: Signup response status
- Current behavior: Signup returns HTTP 200 through ResponseEntity.ok.
- Follow-up: Decide and document the API contract; HTTP 201 Created may be more appropriate for successful resource creation.

## 3. Testing Gaps

- Only one test currently exists: `BackendApplicationTests.contextLoads`.
- Signup and login are not covered by dedicated tests.
- Invalid credentials and duplicate registration are not covered.
- JWT expiry and malformed-token behavior are not covered.
- Authorization and account-status behavior are not covered.

## 4. Configuration

- `application-dev.yml` currently defines the datasource URL and username directly.
- `DB_URL` and `DB_USERNAME` are not currently consumed by this configuration.
- No separate production profile has been verified.
- Do not commit real credentials or secrets.

## 5. Build Warnings

The latest successful build reported:
- Deprecated API usage in `JwtAuthenticationFilter`.
- Mockito dynamic Java-agent attachment warnings.

These warnings did not fail the test run.

## 6. Resolved Environment Issue

The earlier JDBC connection failure was resolved.

Verified on 2026-11-06:
- MySQL connection established.
- Flyway validated V1.
- Schema `aijobpilot` was up to date.
- Hibernate initialized successfully.
- `BackendApplicationTests.contextLoads` passed.

## 7. Current Test Baseline

```text
Tests run: 1
Failures: 0
Errors: 0
Skipped: 0
BUILD SUCCESS
```

This is a startup test baseline, not full functional or security verification.
