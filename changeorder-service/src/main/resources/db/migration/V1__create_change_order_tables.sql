CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE change_orders
(
    id                  UUID PRIMARY KEY      DEFAULT gen_random_uuid(),

    change_order_number VARCHAR(50)  NOT NULL UNIQUE,

    label_change_id     UUID         NOT NULL,
    label_change_number VARCHAR(50)  NOT NULL,

    registration_id     UUID         NOT NULL,
    registration_number VARCHAR(100) NOT NULL,

    country_code        VARCHAR(20)  NOT NULL,
    country_name        VARCHAR(100) NOT NULL,

    product_family_id   UUID         NOT NULL,
    product_family_name VARCHAR(255) NOT NULL,

    dispatch_event_id   VARCHAR(100),
    dispatch_date       TIMESTAMP,

    status              VARCHAR(50)  NOT NULL,

    created_at          TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by          VARCHAR(255),

    updated_at          TIMESTAMP,
    updated_by          VARCHAR(255),

    source_service VARCHAR(50) NOT NULL DEFAULT 'LABELCHANGE-SERVICE'
);

CREATE INDEX idx_change_orders_label_change
    ON change_orders (label_change_id);

CREATE INDEX idx_change_orders_registration
    ON change_orders (registration_id);

CREATE INDEX idx_change_orders_status
    ON change_orders (status);