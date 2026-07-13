package com.mshop.app.user.exception;

import com.mshop.app.common.core.constant.ErrorMessage;
import com.mshop.app.common.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KeycloakCode implements ErrorCode {
    KEYCLOAK_MISSING_LOCATION_HEADER("KC_001", ErrorMessage.GENERIC),
    // keycloak response
    KEYCLOAK_BAD_REQUEST("KC_002", ErrorMessage.GENERIC),
    KEYCLOAK_FORBIDDEN("KC_003", ErrorMessage.GENERIC),
    KEYCLOAK_NOT_FOUND("KC_004", "The requested user account could not be found."),
    KEYCLOAK_CONFLICT("KC_005", "An account with this information already exists."),
    KEYCLOAK_ERROR("KC_006", ErrorMessage.GENERIC),
    KEYCLOAK_UNAUTHORIZED("KC_007", ErrorMessage.GENERIC),
    ;

    private final String code;
    private final String message;
}
