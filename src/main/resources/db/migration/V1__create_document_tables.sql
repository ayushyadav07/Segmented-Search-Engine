CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE documents
(
    id UUID PRIMARY KEY,

    url VARCHAR(2048) NOT NULL UNIQUE,

    status VARCHAR(40) NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    version BIGINT NOT NULL
);

CREATE INDEX idx_documents_status
ON documents(status);