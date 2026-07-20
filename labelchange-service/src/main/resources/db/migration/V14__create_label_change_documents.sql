CREATE TABLE label_change_documents
(
    id                        UUID PRIMARY KEY,

    label_change_id           UUID         NOT NULL UNIQUE,

    document_number           VARCHAR(255) NOT NULL,

    document_version          VARCHAR(100) NOT NULL,

    document_status           VARCHAR(30)  NOT NULL,

    document_type             VARCHAR(255),

    document_url              VARCHAR(2000),

    actual_ccds_review_date   DATE,

    actual_ccds_approval_date DATE,

    event_id                  VARCHAR(255),

    dispatch_date             DATE,

    dispatch_by               VARCHAR(255),

    created_at                TIMESTAMP    NOT NULL,

    created_by                VARCHAR(255),

    updated_at                TIMESTAMP,

    updated_by                VARCHAR(255),

    CONSTRAINT fk_lcd_label_change
        FOREIGN KEY (label_change_id)
            REFERENCES label_changes (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_lcd_label_change
    ON label_change_documents (label_change_id);

CREATE INDEX idx_lcd_document_status
    ON label_change_documents (document_status);