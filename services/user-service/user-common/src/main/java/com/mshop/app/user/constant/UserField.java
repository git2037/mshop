package com.mshop.app.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@RequiredArgsConstructor
@Slf4j
public enum UserField {
    ID("id"),
    KEYCLOAK_ID("keycloak-id"),
    EMAIL("email"),
    FULL_NAME("full-name"),
    PHONE_NUMBER("phone-number"),
    CREATED_AT("created-at"),
    UPDATED_AT("updated-at"),
    CREATED_BY("created-by"),
    UPDATED_BY("updated-by"),
    DELETED("deleted");
    ;

    private final String field;

    public static UserField fromValue(String value) {
        for (UserField field : UserField.values()) {
            if (field.field.equalsIgnoreCase(value)) {
                return field;
            }
        }
        log.error("Not supported field: {}", value);
        throw new IllegalArgumentException("Unknown field: " + value);
    }
}
