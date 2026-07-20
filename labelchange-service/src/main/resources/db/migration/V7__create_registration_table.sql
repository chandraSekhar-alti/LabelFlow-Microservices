CREATE TABLE registrations
(
    id                  UUID PRIMARY KEY,

    registration_code   VARCHAR(30)  NOT NULL UNIQUE,

    registration_number VARCHAR(100),

    application_number  VARCHAR(100),

    trade_name          VARCHAR(200) NOT NULL,

    package_name        VARCHAR(200),

    product_id          UUID         NOT NULL,

    country_id          SMALLINT     NOT NULL,

    is_active           BOOLEAN      NOT NULL DEFAULT TRUE,

    created_at          TIMESTAMP    NOT NULL,

    created_by          VARCHAR(100),

    updated_at          TIMESTAMP,

    updated_by          VARCHAR(100),

    CONSTRAINT fk_registration_product
        FOREIGN KEY (product_id)
            REFERENCES products (id),

    CONSTRAINT fk_registration_country
        FOREIGN KEY (country_id)
            REFERENCES countries (id)
);