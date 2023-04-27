-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    address_id bigint not null,
    name varchar(50)
);

create sequence address_SEQ start with 1 increment by 1;

create table address
(
    id bigint not null primary key,
    street varchar(255)
);

create sequence phone_SEQ start with 1 increment by 1;

create table phone
(
    id bigint not null primary key,
    client_id bigint not null,
    number varchar(50)
);

ALTER TABLE client
    ADD CONSTRAINT fk_client_address FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE phone
    ADD CONSTRAINT fk_phone_client FOREIGN KEY (client_id) REFERENCES client (id);

