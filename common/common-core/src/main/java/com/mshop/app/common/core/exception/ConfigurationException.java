package com.mshop.app.common.core.exception;

public class ConfigurationException extends SystemException{
    public ConfigurationException(ErrorCode code) {
        super(code);
    }

    public ConfigurationException(ErrorCode code, String message) {
        super(code, message);
    }

    public ConfigurationException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
