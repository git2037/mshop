package com.mshop.app.kafka.exception;

import com.mshop.app.common.core.constant.ErrorMessage;
import com.mshop.app.common.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum KafkaCode implements ErrorCode {
    INVALID_EVENT_TYPE("KFK_001", ErrorMessage.GENERIC);

    private final String code;
    private final String message;
}
