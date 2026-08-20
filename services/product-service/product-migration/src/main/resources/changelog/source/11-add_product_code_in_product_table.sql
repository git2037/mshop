alter table product
    add column code varchar(255) not null;

alter table product
    add constraint unique_code
        unique (code);