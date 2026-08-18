ALTER TABLE product_image
    ADD INDEX fk_product_id (product_id);

ALTER TABLE product_image
    DROP INDEX product_id;

ALTER TABLE product_image
    DROP COLUMN is_thumbnail;

alter table product_image
    change url file_name varchar(255) not null;

alter table product_image
    add constraint unique_url_product_id
        unique (file_name, product_id)