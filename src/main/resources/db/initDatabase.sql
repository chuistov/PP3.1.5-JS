
create schema if not exists repository;
create table if not exists repository.role
(
    id int auto_increment primary key,
    role_name varchar(255) not null unique
    );

create table if not exists repository.user
(
    id bigint auto_increment primary key,
    name      varchar(255) not null,
    last_name varchar(255) not null,
    age       int          not null,
    email     varchar(255) not null unique,
    password  varchar(255) not null
    );

create table if not exists repository.users_roles
(
    id bigint auto_increment primary key,
    user_id bigint references repository.user (id),
    role_id int    references repository.role (id)
    );
