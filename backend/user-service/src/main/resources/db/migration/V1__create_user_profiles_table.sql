CREATE TABLE user_profiles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    headline VARCHAR(255),
    summary TEXT,
    location VARCHAR(150),
    phone VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_user_profiles PRIMARY KEY (id),
    CONSTRAINT uk_user_profiles_user_id UNIQUE (user_id)
);
