CREATE TABLE change_orders
(
    id                           UUID PRIMARY KEY,

    change_order_number          VARCHAR(50) NOT NULL UNIQUE,

    label_change_id              UUID        NOT NULL,

    label_change_registration_id UUID        NOT NULL,

    status                       VARCHAR(30) NOT NULL,

    created_at                   TIMESTAMP   NOT NULL,
    created_by                   VARCHAR(255),
    updated_at                   TIMESTAMP,
    updated_by                   VARCHAR(255),

    CONSTRAINT fk_change_order_label_change
        FOREIGN KEY (label_change_id)
            REFERENCES label_changes (id),

    CONSTRAINT fk_change_order_registration
        FOREIGN KEY (label_change_registration_id)
            REFERENCES label_change_registrations (id)
);

CREATE INDEX idx_change_order_label_change
    ON change_orders (label_change_id);

CREATE INDEX idx_change_order_registration
    ON change_orders (label_change_registration_id);

CREATE INDEX idx_change_order_status
    ON change_orders (status);