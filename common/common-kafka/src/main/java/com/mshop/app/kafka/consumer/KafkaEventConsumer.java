package com.mshop.app.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshop.app.kafka.config.KafkaObjectMapper;
import com.mshop.app.kafka.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final EventHandlerRegistry handlerRegistry;

    public void consume(Event<?> rawEvent) {
        EventHandler<?> handler = handlerRegistry.get(rawEvent.getEventType());
        Object payloadObj = rawEvent.getPayload();
        ObjectMapper om = KafkaObjectMapper.getObjectMapper();

        Object convertedPayload = om.convertValue(
                payloadObj,
                handler.payloadType()
        );

        handleWithCast(handler, rawEvent, convertedPayload);
    }

    @SuppressWarnings("unchecked")
    private <T> void handleWithCast(
            EventHandler<T> handler,
            Event<?> rawEvent,
            Object payload
    ) {
        Event<T> event = (Event<T>) Event.builder().eventId(rawEvent.getEventId())
                .eventType(rawEvent.getEventType())
                .objectId(rawEvent.getObjectId())
                .timestamp(rawEvent.getTimestamp())
                .payload(payload).build();

        handler.handle(event);
    }
}
