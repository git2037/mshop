package com.mshop.app.user.model;

import com.mshop.app.common.core.response.BaseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.Map;

@SuperBuilder
@Getter
@Setter
public class OutboxEvent extends BaseDto {
    private final String eventType;
    private final String objectId;
    private final Map<String, Object> payload;
    private final Instant sentAt;
    private final Integer retryCount;
}