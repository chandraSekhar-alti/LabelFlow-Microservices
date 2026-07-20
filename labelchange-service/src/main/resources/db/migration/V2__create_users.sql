CREATE TABLE users
(
    id UUID PRIMARY KEY,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    email VARCHAR(255) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    account_locked BOOLEAN NOT NULL DEFAULT FALSE,

    failed_login_attempts INTEGER NOT NULL DEFAULT 0
        CHECK (failed_login_attempts >= 0),

    last_login TIMESTAMP,

    password_changed_at TIMESTAMP,

    created_at TIMESTAMP NOT NULL,

    created_by VARCHAR(100),

    updated_at TIMESTAMP,

    updated_by VARCHAR(100)
);