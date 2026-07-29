package com.mshop.app.user.consumer;

import com.mshop.app.kafka.consumer.EventHandler;
import com.mshop.app.kafka.event.Event;
import com.mshop.app.user.constant.UserEventType;
import com.mshop.app.user.payload.KeycloakDeleteEvent;
import com.mshop.app.user.repository.KeycloakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserDeleteHandler implements EventHandler<KeycloakDeleteEvent> {

    private final KeycloakRepository keycloakRepository;

    @Override
    public String eventType() {
        return UserEventType.KEYCLOAK_USER_DELETED.name();
    }

    @Override
    public Class<KeycloakDeleteEvent> payloadType() {
        return KeycloakDeleteEvent.class;
    }

    @Override
    public void handle(Event<KeycloakDeleteEvent> event) {
        log.info("Received keycloak delete account event id={}", event.getEventId());
        String keycloakId = event.getPayload().getKeycloakId();
        keycloakRepository.deleteAccount(keycloakId);
        log.info("Successfully processed delete account event id={}", event.getEventId());
    }
}
