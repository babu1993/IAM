create schema if not exists identity;

create table IF NOT EXISTS identity.identities (
    id uuid primary key,
    name text not null
);