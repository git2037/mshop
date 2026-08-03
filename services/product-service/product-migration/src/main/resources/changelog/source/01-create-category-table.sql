CREATE TABLE IF NOT EXISTS category
(
    id         char(36) primary key,
    name       varchar(255) not null,
    code       varchar(255) not null,
    parent_id  varchar(36),
    path       varchar(255) not null unique,
    created_at timestamp    not null,
    created_by varchar(255) not null,
    updated_at timestamp    not null,
    updated_by varchar(255) not null,
    deleted    timestamp
);

alter table category
add constraint unique (code, name, path)