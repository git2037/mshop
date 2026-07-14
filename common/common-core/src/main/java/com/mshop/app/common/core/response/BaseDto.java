package com.mshop.app.common.core.response;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseDto {
    String id;
    LocalDateTime createdAt;
    String createdBy;
    LocalDateTime updatedAt;
    String updatedBy;
    LocalDateTime deleted;
}