CREATE TABLE label_change_tracking_decisions
(
    id                UUID PRIMARY KEY,

    label_change_id   UUID        NOT NULL,

    tracking_decision VARCHAR(30) NOT NULL,

    comments          VARCHAR(1000),

    decided_by        UUID        NOT NULL,

    decided_at        TIMESTAMP   NOT NULL,

    created_at        TIMESTAMP   NOT NULL,

    created_by        VARCHAR(100),

    updated_at        TIMESTAMP,

    updated_by        VARCHAR(100),

    CONSTRAINT fk_lctd_label_change
        FOREIGN KEY (label_change_id)
            REFERENCES label_changes (id),

    CONSTRAINT fk_lctd_user
        FOREIGN KEY (decided_by)
            REFERENCES users (id)
);