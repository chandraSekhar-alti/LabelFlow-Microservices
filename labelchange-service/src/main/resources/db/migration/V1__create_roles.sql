CREATE TABLE roles
(
    id          SMALLINT PRIMARY KEY,

    role_name   VARCHAR(50) NOT NULL UNIQUE,

    description VARCHAR(255),

    is_active   BOOLEAN     NOT NULL DEFAULT TRUE,

    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,

    created_by  VARCHAR(100),

    updated_at  TIMESTAMP,

    updated_by  VARCHAR(100)
);

INSERT INTO roles
    (id, role_name, description)
VALUES (1, 'ROLE_RA', 'Regulatory Affairs'),
       (2, 'ROLE_GL', 'Global Labeling');