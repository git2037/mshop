alter table attribute_value
    add column value_code varchar(255) not null;

alter table attribute_value
    add constraint unique_value_code
        unique (value_code);