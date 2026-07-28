create table if not exists user_job(
    name          char(255) primary key,
    last_processed_time bigint not null
);

insert into user_job(name, last_processed_time)
values ('sync-missing-profiles', 0);