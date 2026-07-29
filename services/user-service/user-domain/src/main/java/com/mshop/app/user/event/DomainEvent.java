package com.mshop.app.user.event;

public interface DomainEvent {
    String eventType();

    String objectId();
}
