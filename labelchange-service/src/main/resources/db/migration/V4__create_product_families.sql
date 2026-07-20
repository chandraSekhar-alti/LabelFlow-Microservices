CREATE TABLE product_families
(
    id              UUID PRIMARY KEY,

    family_code     VARCHAR(20) NOT NULL UNIQUE,

    family_name     VARCHAR(150) NOT NULL UNIQUE,

    description     VARCHAR(500),

    is_active       BOOLEAN NOT NULL DEFAULT TRUE,

    created_at      TIMESTAMP NOT NULL,
    created_by      VARCHAR(100),

    updated_at      TIMESTAMP,
    updated_by      VARCHAR(100)
);