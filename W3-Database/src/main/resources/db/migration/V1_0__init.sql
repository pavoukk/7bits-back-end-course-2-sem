CREATE TABLE task (
id CHAR(36) PRIMARY KEY,
task VARCHAR NOT NULL,
status VARCHAR NOT NULL DEFAULT "inbox",
created_at TIMESTAMPTZ NOT NULL
);