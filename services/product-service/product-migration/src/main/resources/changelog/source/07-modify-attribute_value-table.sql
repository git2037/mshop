alter table attribute_value
    drop foreign key attribute_value_ibfk_1;

alter table attribute_value
    drop column attribute_id;

drop index attribute_id on attribute_value;

alter table attribute_value
    add column attribute_code varchar(255);

alter table attribute_value
    add constraint attribute_code_value_unique unique (attribute_code, value);

alter table attribute_value
    add constraint fk_attribute_value_attribute
        foreign key (attribute_code) references attribute (code);

alter table attribute_value
    drop column product_id;
