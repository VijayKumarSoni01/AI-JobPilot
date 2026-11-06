# AGENTS.md - Multi-AI Handoff & Protocol Guide

> **CRITICAL FOR ALL AI AGENTS**: Read this file and `docs/IMPLEMENTATION_STATUS.md` immediately upon starting a session to understand the exact project state, completed work, and immediate next steps without needing to re-explore the entire codebase.

---

## 🤖 Multi-Agent Handoff Protocol
When token limits are reached or a session ends, the active AI agent must ensure all progress is committed and documented in `docs/IMPLEMENTATION_STATUS.md` and `docs/SESSION_LOG.md`. 

### Protocol for Incoming AI Agents:
1. **Read Core Documentation**:
   - [`AGENTS.md`](./AGENTS.md) (this file)
   - [`docs/IMPLEMENTATION_STATUS.md`](./docs/IMPLEMENTATION_STATUS.md) (what is done vs pending)
   - [`docs/PROJECT_OVERVIEW.md`](./docs/PROJECT_OVERVIEW.md) (high-level goals)
   - [`docs/ARCHITECTURE.md`](./docs/ARCHITECTURE.md) (system design)
2. **Verify Environment**:
   - Check Java 21 & Maven (`./mvnw`).
   - Ensure MySQL connection and `DB_PASSWORD` environment variable are set.
3. **Execute Next Task**:
   - Pick up directly from the **Pending / Next Steps** section in `docs/IMPLEMENTATION_STATUS.md`.

---

## 🛠️ Repository Quick Reference
- **GitHub Repository**: [VijayKumarSoni01/AI-JobPilot](https://github.com/VijayKumarSoni01/AI-JobPilot)
- **Local Working Directory**: `D:\AI\AI-JobPilot`
- **Tech Stack**:
  - **Backend**: Spring Boot, Java 21, Maven, Spring Data JPA, Spring Security, Flyway, MySQL, Lombok, Actuator.
  - **Frontend**: Not yet initialized (`frontend/`).
- **Build & Test**:
  ```powershell
  cd backend
  ./mvnw clean test
  ./mvnw spring-boot:run
  ```
