package com.mshop.app.kafka.consumer;

import com.mshop.app.common.core.exception.SystemException;
import com.mshop.app.kafka.exception.KafkaCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class EventHandlerRegistry {
    private final Map<String, EventHandler<?>> handlers = new HashMap<>();

    public EventHandlerRegistry(List<EventHandler<?>> eventHandlerList) {
        for (EventHandler<?> eventHandler : eventHandlerList) {
            handlers.put(eventHandler.eventType(), eventHandler);
        }
    }

    public EventHandler<?> get(String eventType) {
        EventHandler<?> handler = handlers.get(eventType);
        if (handler == null) {
            log.error("Failed to get event type of message. Invalid value provided: '{}'", eventType);
            throw new SystemException(KafkaCode.INVALID_EVENT_TYPE);
        }
        return handler;
    }
}
