package com.mshop.app.user.repository;

import com.mshop.app.user.model.OutboxEvent;

import java.util.List;

public interface OutboxRepository {
    void saveAll(List<OutboxEvent> outboxEventList);

    List<OutboxEvent> getEventNotSent(int size);

    void markSent(OutboxEvent outboxEvent);
}
