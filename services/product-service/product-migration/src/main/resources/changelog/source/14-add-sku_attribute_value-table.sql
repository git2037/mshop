CREATE TABLE IF NOT EXISTS sku_attribute_value
(
    id                 char(36) primary key,
    created_at         timestamp    not null,
    updated_at         timestamp    not null,
    deleted            timestamp,
    sku_id             char(36),
    attribute_value_id char(36)
);

ALTER TABLE sku_attribute_value
    ADD constraint fk_sku
        FOREIGN KEY (sku_id) REFERENCES sku(id);

ALTER TABLE sku_attribute_value
    ADD constraint fk_attribute_value
        FOREIGN KEY (attribute_value_id) REFERENCES attribute_value(id);

ALTER TABLE sku_attribute_value
    ADD constraint unique_sku_attribute_value
        UNIQUE (sku_id, attribute_value_id);