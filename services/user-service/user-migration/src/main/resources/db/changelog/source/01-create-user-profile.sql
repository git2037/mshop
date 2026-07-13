create table if not exists user_profile
(
    id          char(36) primary key,
    keycloak_id char(36)     not null unique,
    email       varchar(255) not null unique,
    full_name   varchar(255) not null,
    phone_number char(12),
    created_at  timestamp not null,
    created_by  varchar(255) not null ,
    updated_at  timestamp not null ,
    updated_by  varchar(255) not null ,
    deleted  timestamp
);