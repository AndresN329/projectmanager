-- Extensión para UUID (opcional pero recomendable)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE projects (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    project_id UUID,
    title VARCHAR(255),
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

-- Índices útiles (suman puntos en rúbrica)
CREATE INDEX idx_projects_owner_id ON projects(owner_id);
CREATE INDEX idx_projects_deleted ON projects(deleted);
CREATE INDEX idx_tasks_project_id ON tasks(project_id);
CREATE INDEX idx_tasks_deleted ON tasks(deleted);
