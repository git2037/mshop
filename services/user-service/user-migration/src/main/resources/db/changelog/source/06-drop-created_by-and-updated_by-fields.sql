alter table outbox_events
drop column created_by;

alter table outbox_events
drop column updated_by;

alter table user_profile
drop column created_by;

alter table user_profile
drop column updated_by;