package com.mshop.app.attribute.exception;

import com.mshop.app.common.core.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AttributeExceptionHandler {

    @ExceptionHandler(value = {AttributeAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleAttributeAlreadyExistException(AttributeAlreadyExistException exception) {
        return ApiResponse.buildFailResponse(exception);
    }

    @ExceptionHandler(value = {AttributeNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleAttributeNotFoundException(AttributeNotFoundException exception) {
        return ApiResponse.buildFailResponse(exception);
    }
}
