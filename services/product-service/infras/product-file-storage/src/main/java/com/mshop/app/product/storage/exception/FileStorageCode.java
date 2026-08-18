package com.mshop.app.product.storage.exception;

import com.mshop.app.common.core.constant.ErrorMessage;
import com.mshop.app.common.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileStorageCode implements ErrorCode {
    MINIO_ERROR_RESPONSE("STORAGE_001", ErrorMessage.GENERIC),
    MINIO_COMMUNICATE_ERROR("STORAGE_002", ErrorMessage.GENERIC),
    MINIO_SDK_ERROR("STORAGE_003", ErrorMessage.GENERIC),
    ;
    private final String code;
    private final String message;
    }
