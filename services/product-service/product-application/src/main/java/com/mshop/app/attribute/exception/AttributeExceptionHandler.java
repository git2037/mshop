package com.mshop.app.attribute.exception;

import com.mshop.app.common.core.exception.AppException;
import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AttributeExceptionHandler {

    @ExceptionHandler(value = {AttributeAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleAttributeAlreadyExistException(AttributeAlreadyExistException e) {
        ErrorCode errorCode = e.getCode();
        String message = getMessage(errorCode, e);
        return ApiResponse.buildFailResponse(errorCode.getCode(), message);
    }

    private String getMessage(ErrorCode errorCode, AppException exception) {
        return exception.getMessage() == null ? errorCode.getMessage() : exception.getMessage();
    }
}
