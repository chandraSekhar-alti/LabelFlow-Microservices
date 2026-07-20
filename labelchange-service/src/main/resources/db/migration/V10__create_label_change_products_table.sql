CREATE TABLE label_change_products
(
    id              UUID PRIMARY KEY,

    label_change_id UUID      NOT NULL,

    product_id      UUID      NOT NULL,

    created_at      TIMESTAMP NOT NULL,

    created_by      VARCHAR(100),

    updated_at      TIMESTAMP,

    updated_by      VARCHAR(100),

    CONSTRAINT fk_label_change_product_label_change
        FOREIGN KEY (label_change_id)
            REFERENCES label_changes (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_label_change_product_product
        FOREIGN KEY (product_id)
            REFERENCES products (id),

    CONSTRAINT uk_label_change_product
        UNIQUE (label_change_id, product_id)
);

CREATE INDEX idx_label_change_product_label_change
    ON label_change_products (label_change_id);

CREATE INDEX idx_label_change_product_product
    ON label_change_products (product_id);