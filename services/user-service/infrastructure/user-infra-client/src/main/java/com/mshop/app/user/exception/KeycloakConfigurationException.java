package com.mshop.app.user.exception;

import com.mshop.app.common.core.exception.ConfigurationException;
import com.mshop.app.common.core.exception.ErrorCode;

public class KeycloakConfigurationException extends ConfigurationException {
    public KeycloakConfigurationException(ErrorCode code) {
        super(code);
    }

    public KeycloakConfigurationException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }

    public KeycloakConfigurationException(ErrorCode code, String message) {
        super(code, message);
    }
}
