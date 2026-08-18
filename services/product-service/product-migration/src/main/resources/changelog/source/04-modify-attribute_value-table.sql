alter table attribute_value
    modify value varchar(255) not null;

alter table attribute_value
    add column product_id char(36);

alter table attribute_value
    add foreign key (product_id) references product(id);
