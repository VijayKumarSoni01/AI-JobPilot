-- AI-JobPilot MySQL Initialization Script
-- Creates service-owned databases and least-privilege service users

CREATE DATABASE IF NOT EXISTS auth_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS user_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS resume_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS job_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS matching_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS ai_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS application_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS notification_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'auth_user'@'%' IDENTIFIED BY 'auth_password';
GRANT ALL PRIVILEGES ON auth_db.* TO 'auth_user'@'%';

CREATE USER IF NOT EXISTS 'user_user'@'%' IDENTIFIED BY 'user_password';
GRANT ALL PRIVILEGES ON user_db.* TO 'user_user'@'%';

CREATE USER IF NOT EXISTS 'resume_user'@'%' IDENTIFIED BY 'resume_password';
GRANT ALL PRIVILEGES ON resume_db.* TO 'resume_user'@'%';

CREATE USER IF NOT EXISTS 'job_user'@'%' IDENTIFIED BY 'job_password';
GRANT ALL PRIVILEGES ON job_db.* TO 'job_user'@'%';

CREATE USER IF NOT EXISTS 'matching_user'@'%' IDENTIFIED BY 'matching_password';
GRANT ALL PRIVILEGES ON matching_db.* TO 'matching_user'@'%';

CREATE USER IF NOT EXISTS 'ai_user'@'%' IDENTIFIED BY 'ai_password';
GRANT ALL PRIVILEGES ON ai_db.* TO 'ai_user'@'%';

CREATE USER IF NOT EXISTS 'application_user'@'%' IDENTIFIED BY 'application_password';
GRANT ALL PRIVILEGES ON application_db.* TO 'application_user'@'%';

CREATE USER IF NOT EXISTS 'notification_user'@'%' IDENTIFIED BY 'notification_password';
GRANT ALL PRIVILEGES ON notification_db.* TO 'notification_user'@'%';

FLUSH PRIVILEGES;
