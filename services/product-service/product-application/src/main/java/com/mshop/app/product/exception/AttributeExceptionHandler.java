package com.mshop.app.product.exception;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.product.exception.attribute.AttributeAlreadyDisableException;
import com.mshop.app.product.exception.attribute.AttributeAlreadyExistException;
import com.mshop.app.product.exception.attribute.AttributeNotFoundException;
import com.mshop.app.product.exception.attribute.AttributeValueAlreadyExistException;
import com.mshop.app.product.exception.attribute.AttributeValueNotFoundException;
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

    @ExceptionHandler(value = {AttributeAlreadyDisableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleAttributeAlreadyDisableException(AttributeAlreadyDisableException exception) {
        return ApiResponse.buildFailResponse(exception);
    }

    @ExceptionHandler(value = {AttributeValueAlreadyExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleAttributeValueAlreadyExistException(AttributeValueAlreadyExistException exception) {
        return ApiResponse.buildFailResponse(exception);
    }

    @ExceptionHandler(value = {AttributeValueNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleAttributeValueNotFoundException(AttributeValueNotFoundException exception) {
        return ApiResponse.buildFailResponse(exception);
    }
}
