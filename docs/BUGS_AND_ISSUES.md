# Bugs and Known Issues - AI JobPilot

## Active Issues
*None currently.*

## Resolved / Configuration Notes
- **Database Password (`DB_PASSWORD`)**: Ensure the environment variable `DB_PASSWORD` is set in your local shell before running Spring Boot, or update `application.yaml` with your local MySQL root password.
  ```powershell
  $env:DB_PASSWORD="your_mysql_password"
  ```
- **MySQL Service**: Ensure MySQL server is running locally on port `3306` and the `aijobpilot` database has been created.
  ```sql
  CREATE DATABASE aijobpilot;
  ```
