create table test
(
    id   int,
    name varchar(50)
);
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);
CREATE TABLE manager
(
    no bigserial not null primary key,
    label VARCHAR(255) UNIQUE NOT NULL,
    param1 VARCHAR(255)
);
