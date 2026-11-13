# Authentication Sequence

```mermaid
sequenceDiagram
    participant C as Client
    participant A as AuthController
    participant S as AuthService
    participant U as UserRepository
    participant T as RefreshTokenRepository
    participant J as JwtService
    participant DB as MySQL

    C->>A: POST /api/auth/login
    A->>S: login(credentials)
    S->>U: findByEmail(email)
    U->>DB: SELECT user
    DB-->>U: user/status/hash
    S->>S: AuthenticationManager verifies BCrypt password
    S->>J: generateToken(userDetails)
    J-->>S: access JWT
    S->>T: save hashed refresh token
    T->>DB: INSERT refresh_tokens
    S-->>A: AuthResponse(access + refresh)
    A-->>C: 200 JSON
```

Refresh-token rotation revokes the consumed token under a transaction and creates a replacement. Logout revokes the supplied refresh token; it does not require an access token in the current API contract.
