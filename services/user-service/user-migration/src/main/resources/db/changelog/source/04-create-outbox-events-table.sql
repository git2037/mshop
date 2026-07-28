create table if not exists outbox_events
(
    id          char(36) primary key,
    event_id    char(36) not null,
    event_type  varchar(50) not null,
    object_id   char(36),
    payload     json,
    sent_at     timestamp,
    retry_count int default 0 not null,
    created_at  timestamp not null,
    created_by  varchar(255) not null,
    updated_at  timestamp not null,
    updated_by  varchar(255) not null,
    deleted  timestamp
);