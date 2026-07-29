package com.mshop.app.user.payload;

import com.mshop.app.user.constant.UserEventType;
import com.mshop.app.user.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KeycloakDisableEvent implements DomainEvent {

    private String keycloakId;
    private int version;

    @Override
    public String eventType() {
        return UserEventType.DISABLE_USER.name();
    }

    @Override
    public String objectId() {
        return this.keycloakId;
    }
}
