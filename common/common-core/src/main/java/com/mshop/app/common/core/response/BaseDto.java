package com.mshop.app.common.core.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@SuperBuilder
@Getter
@Setter
public class BaseDto {
    String id;
    Instant createdAt;
    Instant updatedAt;
    Instant deleted;
}