CREATE TABLE label_changes
(
    id                  UUID PRIMARY KEY,

    label_change_number VARCHAR(30)  NOT NULL UNIQUE,

    product_family_id   UUID         NOT NULL,

    trigger_date        DATE         NOT NULL,

    start_date          DATE,

    change_type         VARCHAR(30)  NOT NULL,

    process_impacted    VARCHAR(30)  NOT NULL,

    change_category     VARCHAR(30)  NOT NULL,

    trigger_type        VARCHAR(50)  NOT NULL,

    signal              VARCHAR(30)  NOT NULL,

    short_description   VARCHAR(500) NOT NULL,

    description         TEXT,

    total_registrations INTEGER      NOT NULL,

    status              VARCHAR(30)  NOT NULL,

    created_at          TIMESTAMP    NOT NULL,

    created_by          VARCHAR(100),

    updated_at          TIMESTAMP,

    updated_by          VARCHAR(100),

    CONSTRAINT fk_label_change_product_family
        FOREIGN KEY (product_family_id)
            REFERENCES product_families (id)
);