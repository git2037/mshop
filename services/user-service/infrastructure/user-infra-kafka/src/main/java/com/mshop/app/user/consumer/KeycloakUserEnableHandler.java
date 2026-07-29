package com.mshop.app.user.consumer;

import com.mshop.app.kafka.consumer.EventHandler;
import com.mshop.app.kafka.event.Event;
import com.mshop.app.user.constant.UserEventType;
import com.mshop.app.user.model.User;
import com.mshop.app.user.payload.KeycloakEnableEvent;
import com.mshop.app.user.repository.KeycloakRepository;
import com.mshop.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserEnableHandler implements EventHandler<KeycloakEnableEvent> {

    private final KeycloakRepository keycloakRepository;
    private final UserService userService;

    @Override
    public String eventType() {
        return UserEventType.ENABLE_USER.name();
    }

    @Override
    public Class<KeycloakEnableEvent> payloadType() {
        return KeycloakEnableEvent.class;
    }

    @Override
    public void handle(Event<KeycloakEnableEvent> event) {
        String eventId = event.getEventId();
        log.info("Received keycloak enable account event id={}", eventId);
        KeycloakEnableEvent payload = event.getPayload();
        User user = userService.findByKeycloakId(payload.getKeycloakId());

        if (payload.getVersion() == user.getVersion()) {
            keycloakRepository.enableAccount(payload.getKeycloakId());
            log.info("Successfully processed enable account event id={}", eventId);
        } else {
            log.warn("Event id={} was already processed. Skipping execution.", eventId);
        }
    }
}
