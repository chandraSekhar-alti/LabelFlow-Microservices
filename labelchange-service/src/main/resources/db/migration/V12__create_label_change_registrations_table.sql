CREATE TABLE label_change_registrations
(
    id              UUID PRIMARY KEY,

    label_change_id UUID      NOT NULL,

    registration_id UUID      NOT NULL,

    created_at      TIMESTAMP NOT NULL,

    created_by      VARCHAR(100),

    updated_at      TIMESTAMP,

    updated_by      VARCHAR(100),

    CONSTRAINT fk_label_change_registration_label_change
        FOREIGN KEY (label_change_id)
            REFERENCES label_changes (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_label_change_registration_registration
        FOREIGN KEY (registration_id)
            REFERENCES registrations (id),

    CONSTRAINT uk_label_change_registration
        UNIQUE (label_change_id, registration_id)
);

CREATE INDEX idx_label_change_registration_label_change
    ON label_change_registrations (label_change_id);

CREATE INDEX idx_label_change_registration_registration
    ON label_change_registrations (registration_id);