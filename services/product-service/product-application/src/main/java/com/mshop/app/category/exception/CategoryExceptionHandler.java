package com.mshop.app.category.exception;

import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CategoryExceptionHandler {

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleCategoryNotFoundException(CategoryNotFoundException e) {
        ErrorCode errorCode = e.getCode();
        return ApiResponse.buildFailResponse(errorCode.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {CategoryAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleCategoryAlreadyExistException(CategoryAlreadyExistException e) {
        return ApiResponse.buildFailResponse(e);
    }

    @ExceptionHandler(value = {CategoryNotMoveException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleCategoryCanNotMove(CategoryNotMoveException e) {
        return ApiResponse.buildFailResponse(e);
    }

    @ExceptionHandler(value = {CategoryNotLeafException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleCategoryNotLeafException(CategoryNotLeafException e) {
        ErrorCode errorCode = e.getCode();
        return ApiResponse.buildFailResponse(errorCode.getCode(), e.getMessage());
    }
}
