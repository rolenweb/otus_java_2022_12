create table Client
(
    id    varchar(50) not null primary key,
    name varchar(50)
);

create table Address
(
    id           bigserial   not null primary key,
    street         varchar(50) not null,
    client_id   varchar(50) not null references Client (id)
);
create index idx_address_client_id on address (client_id);

create table Phone
(
    id           bigserial   not null primary key,
    number         varchar(50) not null,
    client_id   varchar(50) not null references Client (id)
);
create index idx_phone_client_id on phone (client_id);
