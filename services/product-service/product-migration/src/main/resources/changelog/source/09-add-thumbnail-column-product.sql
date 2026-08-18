alter table product
    add column thumbnail varchar(255);

alter table product
    add constraint unique_thumbnail
        unique (thumbnail);