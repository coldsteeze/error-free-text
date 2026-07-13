CREATE TABLE tasks
(
    id             UUID PRIMARY KEY,
    text           TEXT      NOT NULL,
    language       VARCHAR   NOT NULL,
    status         VARCHAR   NOT NULL,
    corrected_text TEXT      NOT NULL,
    error_message  TEXT      NOT NULL,
    created_at     TIMESTAMP NOT NULL
)