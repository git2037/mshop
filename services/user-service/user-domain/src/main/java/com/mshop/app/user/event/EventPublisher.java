package com.mshop.app.user.event;

import com.mshop.app.user.model.OutboxEvent;

public interface EventPublisher {
    void send(OutboxEvent event);
}
