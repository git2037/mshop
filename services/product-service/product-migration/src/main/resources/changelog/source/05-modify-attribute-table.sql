alter table attribute
    modify name varchar(255) not null;

alter table attribute
    modify code varchar(255) not null;

alter table attribute
    add column value_type enum('TEXT', 'NUMBER') not null