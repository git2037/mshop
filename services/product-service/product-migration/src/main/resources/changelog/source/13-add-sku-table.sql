CREATE TABLE IF NOT EXISTS sku
(
    id         char(36) primary key,
    created_at timestamp    not null,
    updated_at timestamp    not null,
    deleted    timestamp,
    code       varchar(255) not null,
    stock      int          not null,
    price      float        not null,
    product_id char(36)
);

ALTER TABLE sku
    ADD constraint unique_code
        unique (code);

ALTER TABLE sku
    ADD constraint check_stock
        check ( stock >= 0 );

ALTER TABLE sku
    ADD constraint check_price
        check ( price > 0 );

ALTER TABLE sku
    ADD constraint fk_product
        FOREIGN KEY (product_id) REFERENCES product (id);