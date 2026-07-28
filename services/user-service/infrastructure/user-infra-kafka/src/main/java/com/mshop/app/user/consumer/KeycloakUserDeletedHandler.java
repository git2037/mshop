package com.mshop.app.user.consumer;

import com.mshop.app.kafka.consumer.EventHandler;
import com.mshop.app.kafka.event.Event;
import com.mshop.app.user.constant.UserEventType;
import com.mshop.app.user.payload.KeycloakDeletedPayload;
import com.mshop.app.user.repository.KeycloakRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserDeletedHandler implements EventHandler<KeycloakDeletedPayload> {

    private final KeycloakRepository keycloakRepository;

    @Override
    public String eventType() {
        return UserEventType.KEYCLOAK_USER_DELETED.name();
    }

    @Override
    public Class<KeycloakDeletedPayload> payloadType() {
        return KeycloakDeletedPayload.class;
    }

    @Override
    public void handle(Event<KeycloakDeletedPayload> event) {
        String keycloakId = event.getPayload().getKeycloakId();
        keycloakRepository.deleteAccount(keycloakId);
        log.info("Deleted keycloak account id={} successfully", keycloakId);
    }
}
