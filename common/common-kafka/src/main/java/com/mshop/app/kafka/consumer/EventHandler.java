package com.mshop.app.kafka.consumer;

import com.mshop.app.kafka.event.Event;

public interface EventHandler<T> {
    String eventType();
    Class<T> payloadType();
    void handle(Event<T> event);
}
