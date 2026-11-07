CREATE TABLE job_applications (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    job_title VARCHAR(255) NOT NULL,
    job_description TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'APPLIED',
    applied_date DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_job_applications PRIMARY KEY (id),
    CONSTRAINT fk_job_applications_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT ck_job_applications_status CHECK (status IN ('SAVED', 'APPLIED', 'INTERVIEW', 'OFFER', 'REJECTED', 'WITHDRAWN'))
);

CREATE INDEX idx_job_applications_user_id ON job_applications (user_id);
CREATE INDEX idx_job_applications_user_status ON job_applications (user_id, status);
