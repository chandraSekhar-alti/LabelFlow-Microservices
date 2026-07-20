CREATE TABLE products
(
    id              UUID PRIMARY KEY,

    family_id       UUID NOT NULL,

    product_code    VARCHAR(20) NOT NULL UNIQUE,

    product_name    VARCHAR(200) NOT NULL,

    product_type    VARCHAR(100),

    product_phase   VARCHAR(100),

    is_active       BOOLEAN NOT NULL DEFAULT TRUE,

    created_at      TIMESTAMP NOT NULL,

    created_by      VARCHAR(100),

    updated_at      TIMESTAMP,

    updated_by      VARCHAR(100),

    CONSTRAINT fk_product_family
        FOREIGN KEY (family_id)
            REFERENCES product_families(id)
);