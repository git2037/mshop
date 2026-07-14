package com.mshop.app.common.core.exception;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.exception.SearchConfigurationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ConfigurationException.class,
            SearchConfigurationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleConfigurationException(ConfigurationException ex) {
        log.error("Configuration failure detected!", ex);
        return ApiResponse.buildFailResponse(ex);
    }

    @ExceptionHandler(value = {AppException.class,
            SystemException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleAppException(AppException ex) {
        log.error("Internal Server Error!", ex);
        return ApiResponse.buildFailResponse(ex);
    }

    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidationException(ValidationException ex) {
        return ApiResponse.buildFailResponse(ex);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleException(Exception ex) {
        log.error("Unexpected Error!", ex);
        ErrorCode errorCode = SystemCode.UNEXPECTED_ERROR;
        return ApiResponse.buildFailResponse(errorCode.getCode(), errorCode.getMessage());
    }
}
