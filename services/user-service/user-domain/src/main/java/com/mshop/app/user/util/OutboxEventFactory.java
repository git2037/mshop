package com.mshop.app.user.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshop.app.common.core.config.DBObjectMapper;
import com.mshop.app.user.event.DomainEvent;
import com.mshop.app.user.model.OutboxEvent;
import com.mshop.app.user.model.User;
import com.mshop.app.user.payload.KeycloakDeleteEvent;
import com.mshop.app.user.payload.KeycloakDisableEvent;
import com.mshop.app.user.payload.KeycloakEnableEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OutboxEventFactory {

    private final ObjectMapper om = DBObjectMapper.getObjectMapper();

    private OutboxEvent from(DomainEvent event) {
        Map<String, Object> payloadMap = om.convertValue(event, new TypeReference<>() {});
        return OutboxEvent.builder()
                .eventType(event.eventType())
                .objectId(event.objectId())
                .payload(payloadMap)
                .retryCount(0)
                .build();
    }

    public OutboxEvent keycloakDeleted(String keycloakId) {
        KeycloakDeleteEvent payload = new KeycloakDeleteEvent(keycloakId);
        return from(payload);
    }

    public OutboxEvent keycloakDisable(User disabledUser) {
        KeycloakDisableEvent payload = new KeycloakDisableEvent(
                disabledUser.getKeycloakId(),
                disabledUser.getVersion());
        return from(payload);
    }

    public OutboxEvent keycloakEnable(User enabledUser) {
        KeycloakEnableEvent payload = new KeycloakEnableEvent(
                enabledUser.getKeycloakId(),
                enabledUser.getVersion());
        return from(payload);
    }
}
