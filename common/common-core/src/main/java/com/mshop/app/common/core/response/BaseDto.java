package com.mshop.app.common.core.response;

import java.time.LocalDateTime;

public class BaseDto {
    String id;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
    LocalDateTime deleted;
}