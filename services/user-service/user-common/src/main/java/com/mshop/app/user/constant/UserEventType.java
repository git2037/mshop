package com.mshop.app.user.constant;

public enum UserEventType {
    KEYCLOAK_USER_DELETED,
    ;

    public static UserEventType fromString(String s) {
        try {
            return UserEventType.valueOf(s);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
