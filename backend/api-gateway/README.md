# API Gateway

Standalone Spring Cloud Gateway for Week 3. Run from this directory with `..\mvnw.cmd spring-boot:run`.

Configuration is externalized through environment variables:

- `GATEWAY_PORT` (default `8088`)
- `GATEWAY_AUTH_SERVICE_URL` (default `http://localhost:8081`)
- `GATEWAY_APPLICATION_SERVICE_URL` (default `http://localhost:8080`)
- `JWT_SECRET` (required; at least 32 characters and must match the issuing service)
- `GATEWAY_CORS_ALLOWED_ORIGINS` (default `http://localhost:5173`)
- `GATEWAY_RATE_LIMIT_REPLENISH_RATE` / `GATEWAY_RATE_LIMIT_BURST_CAPACITY`

`/api/auth/login`, `/api/auth/signup`, health, and CORS preflight are public. Other API routes require a valid signed, non-expired Bearer JWT. The gateway removes client-supplied identity headers and only emits `X-Authenticated-Subject` after validation. Downstream services must trust this header only over a private gateway network (prefer mTLS/network policy in production).

The initial rate limiter uses the request IP as key and requires Redis when enabled by a route; configure a Redis-backed `RequestRateLimiter` before production deployment.
