CREATE TABLE client
(
    id   bigserial not null primary key,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE manager
(
    no bigserial not null primary key,
    label VARCHAR(255) UNIQUE NOT NULL,
    param1 VARCHAR(255)
);
