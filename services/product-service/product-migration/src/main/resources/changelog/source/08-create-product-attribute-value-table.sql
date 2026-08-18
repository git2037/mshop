create table product_attribute_value
(
    id                 char(36) primary key,
    created_at         timestamp not null,
    updated_at         timestamp not null,
    deleted            timestamp,
    attribute_value_id char(36),
    product_id         char(36)
);

alter table product_attribute_value
    add constraint fk_attribute_value_id
        foreign key (attribute_value_id) references attribute_value (id);

alter table product_attribute_value
    add constraint fk_product_id
        foreign key (product_id) references product (id);

alter table product_attribute_value
    add constraint unique_attribute_value_product
        unique (attribute_value_id, product_id);