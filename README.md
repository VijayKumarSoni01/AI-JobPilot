# AI JobPilot

AI-powered job application tracker, resume tailoring assistant, and interview preparation platform.

## 📚 Documentation & Multi-Agent Guide
- **[AGENTS.md](./AGENTS.md)**: Multi-agent handoff & continuation protocol.
- **[Implementation Status](./docs/IMPLEMENTATION_STATUS.md)**: Detailed tracking of completed vs pending work.
- **[Project Overview](./docs/PROJECT_OVERVIEW.md)**: High-level goals & features.
- **[Architecture](./docs/ARCHITECTURE.md)**: System design and components.
- **[Database Design](./docs/DATABASE_DESIGN.md)**: MySQL schema & Flyway migrations.
- **[Development Roadmap](./docs/DEVELOPMENT_ROADMAP.md)**: Phase-by-phase implementation plan.
- **[Decisions (ADRs)](./docs/DECISIONS.md)**: Architectural decisions.
- **[Session Log](./docs/SESSION_LOG.md)**: Log of development sessions.

## 🚀 Quick Start
1. **Clone Repository**:
   ```powershell
   git clone https://github.com/VijayKumarSoni01/AI-JobPilot.git
   cd AI-JobPilot
   ```
2. **Configure Database**:
   Ensure MySQL is running and create the database:
   ```sql
   CREATE DATABASE aijobpilot;
   ```
   Set your database password environment variable:
   ```powershell
   $env:DB_PASSWORD="your_password"
   ```
3. **Run Backend**:
   ```powershell
   cd backend
   ./mvnw spring-boot:run
   ```
