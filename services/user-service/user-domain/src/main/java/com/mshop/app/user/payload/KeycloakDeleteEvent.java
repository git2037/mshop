package com.mshop.app.user.payload;

import com.mshop.app.user.constant.UserEventType;
import com.mshop.app.user.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakDeleteEvent implements DomainEvent {
    private String keycloakId;

    @Override
    public String eventType() {
        return UserEventType.KEYCLOAK_USER_DELETED.name();
    }

    @Override
    public String objectId() {
        return this.keycloakId;
    }
}
