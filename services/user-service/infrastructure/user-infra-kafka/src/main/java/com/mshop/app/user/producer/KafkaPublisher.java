package com.mshop.app.user.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshop.app.common.core.config.DBObjectMapper;
import com.mshop.app.kafka.event.Event;
import com.mshop.app.kafka.producer.KafkaEventProducer;
import com.mshop.app.user.constant.UserEventType;
import com.mshop.app.user.event.DomainEvent;
import com.mshop.app.user.event.EventPublisher;
import com.mshop.app.user.model.OutboxEvent;
import com.mshop.app.user.payload.KeycloakDeleteEvent;
import com.mshop.app.user.payload.KeycloakDisableEvent;
import com.mshop.app.user.payload.KeycloakEnableEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaPublisher implements EventPublisher {

    @Value("${kafka.user-topic}")
    private String userTopic;

    private final KafkaEventProducer eventProducer;

    @Override
    public void send(OutboxEvent outboxEvent) {
        Event<?> event = fromOutboxEvent(outboxEvent);
        eventProducer.send(userTopic, event);
    }

    private Event<?> fromOutboxEvent(OutboxEvent outboxEvent) {
        UserEventType userEventType = UserEventType.fromString(outboxEvent.getEventType());
        ObjectMapper om = DBObjectMapper.getObjectMapper();

        DomainEvent event = switch (userEventType) {
            case KEYCLOAK_USER_DELETED -> om.convertValue(outboxEvent.getPayload(), KeycloakDeleteEvent.class);
            case ENABLE_USER -> om.convertValue(outboxEvent.getPayload(), KeycloakEnableEvent.class);
            case DISABLE_USER -> om.convertValue(outboxEvent.getPayload(), KeycloakDisableEvent.class);
        };

        return Event.create(userEventType.name(), outboxEvent.getObjectId(), event);
    }
}
