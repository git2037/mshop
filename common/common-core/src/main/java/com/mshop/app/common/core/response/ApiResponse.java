package com.mshop.app.common.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mshop.app.common.core.exception.AppException;
import com.mshop.app.common.core.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private Map<String, Object> meta;

    public static <T> ApiResponse<T> buidSuccessResponse(String message, T data, Map<String, Object> metadata) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .meta(metadata)
                .build();
    }

    public static <T> ApiResponse<T> buidSuccessResponse(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse<Void> buildFailResponse(String code, String message, Map<String, Object> metadata) {
        return ApiResponse.<Void>builder()
                .code(code)
                .message(message)
                .meta(metadata)
                .build();
    }

    public static ApiResponse<Void> buildFailResponse(String code, String message) {
        return ApiResponse.<Void>builder()
                .code(code)
                .message(message)
                .build();
    }

    public static ApiResponse<Void> buildFailResponse(AppException exception) {
        ErrorCode errorCode = exception.getCode();
        return ApiResponse.buildFailResponse(errorCode.getCode(), errorCode.getMessage());
    }
}
