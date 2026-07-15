package com.mshop.app.common.core.searching.exception;

import com.mshop.app.common.core.exception.ConfigurationException;
import com.mshop.app.common.core.exception.ErrorCode;

import java.util.Map;

public class SearchConfigurationException extends ConfigurationException {
    public SearchConfigurationException(ErrorCode code) {
        super(code);
    }

    public SearchConfigurationException(ErrorCode code, String message) {
        super(code, message);
    }

    public SearchConfigurationException(ErrorCode code, Map<String, Object> params) {
        super(code, MessageBuilder.format(code.getMessage(), params));
    }
}
