package com.mshop.app.user.consumer;

import com.mshop.app.kafka.consumer.EventHandler;
import com.mshop.app.kafka.event.Event;
import com.mshop.app.user.constant.UserEventType;
import com.mshop.app.user.model.User;
import com.mshop.app.user.payload.KeycloakDisableEvent;
import com.mshop.app.user.repository.KeycloakRepository;
import com.mshop.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserDisableHandler implements EventHandler<KeycloakDisableEvent> {

    private final KeycloakRepository keycloakRepository;
    private final UserService userService;

    @Override
    public String eventType() {
        return UserEventType.DISABLE_USER.name();
    }

    @Override
    public Class<KeycloakDisableEvent> payloadType() {
        return KeycloakDisableEvent.class;
    }

    @Override
    public void handle(Event<KeycloakDisableEvent> event) {
        String eventId = event.getEventId();
        log.info("Received keycloak disable account event id={}", eventId);
        KeycloakDisableEvent payload = event.getPayload();
        User user = userService.findByKeycloakId(payload.getKeycloakId());

        if (payload.getVersion() >= user.getVersion()) {
            keycloakRepository.disableAccount(payload.getKeycloakId());
            log.info("Successfully processed disable account event id={}", eventId);
        } else {
            log.warn("Event id={} was already processed. Skipping execution.", eventId);
        }
    }
}
