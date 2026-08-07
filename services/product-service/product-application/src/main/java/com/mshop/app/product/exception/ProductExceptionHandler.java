package com.mshop.app.product.exception;

import com.mshop.app.common.core.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ProductExceptionHandler {

    @ExceptionHandler(value = {ProductAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleProductAlreadyExistException(ProductAlreadyExistException e) {
        return ApiResponse.buildFailResponse(e);
    }

    @ExceptionHandler(value = {ProductNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleProductNotFoundException(ProductNotFoundException e) {
        return ApiResponse.buildFailResponse(e);
    }
}
