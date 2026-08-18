alter table attribute
add constraint name_unique unique (name);

alter table attribute
    add constraint code_unique unique (code);

alter table attribute
    drop column value_type;