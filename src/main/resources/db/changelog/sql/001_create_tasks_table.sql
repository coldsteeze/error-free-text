CREATE TABLE tasks
(
    id             UUID PRIMARY KEY,
    text           TEXT      NOT NULL,
    language       VARCHAR   NOT NULL,
    status         VARCHAR   NOT NULL,
    corrected_text TEXT,
    error_message  TEXT,
    created_at     TIMESTAMP NOT NULL
)