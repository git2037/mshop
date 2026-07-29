alter table user_profile
    add version int default 0;
alter table user_profile
drop column keycloak_disable;