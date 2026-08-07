CREATE TABLE IF NOT EXISTS product
(
    id          char(36) primary key,
    created_at  timestamp    not null,
    created_by  varchar(255) not null,
    updated_at  timestamp    not null,
    updated_by  varchar(255) not null,
    deleted     timestamp,
    name        varchar(255) not null unique,
    description text
    );

CREATE TABLE IF NOT EXISTS product_category
(
    id          char(36) primary key,
    created_at  timestamp    not null,
    created_by  varchar(255) not null,
    updated_at  timestamp    not null,
    updated_by  varchar(255) not null,
    deleted     timestamp,
    category_id char(36),
    product_id  char(36)
    );

CREATE TABLE IF NOT EXISTS attribute
(
    id         char(36) primary key,
    created_at timestamp    not null,
    created_by varchar(255) not null,
    updated_at timestamp    not null,
    updated_by varchar(255) not null,
    deleted    timestamp,
    name       varchar(255),
    code       varchar(255)
    );

CREATE TABLE IF NOT EXISTS attribute_value
(
    id           char(36) primary key,
    created_at   timestamp    not null,
    created_by   varchar(255) not null,
    updated_at   timestamp    not null,
    updated_by   varchar(255) not null,
    deleted      timestamp,
    attribute_id char(36),
    value        varchar(255)
    );

CREATE TABLE IF NOT EXISTS product_image
(
    id          char(36) primary key,
    created_at  timestamp not null,
    created_by  varchar(255) not null ,
    updated_at  timestamp not null ,
    updated_by  varchar(255) not null ,
    deleted     timestamp,
    url         varchar(255),
    product_id  char(36),
    is_thumbnail tinyint(1) null
    );

alter table product_category
    add foreign key (category_id) references category (id);

alter table product_category
    add foreign key (product_id) references product (id);

alter table product_category
    add constraint unique (category_id, product_id);

alter table attribute
    add constraint unique (name, code);

alter table attribute_value
    add foreign key (attribute_id) references attribute (id);

alter table attribute_value
    add constraint unique (attribute_id, value);

alter table product_image
    add foreign key (product_id) references product(id);

alter table product_image
    add unique (product_id, is_thumbnail);