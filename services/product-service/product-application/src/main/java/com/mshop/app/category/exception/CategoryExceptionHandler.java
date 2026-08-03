package com.mshop.app.category.exception;

import com.mshop.app.common.core.exception.SystemCode;
import com.mshop.app.common.core.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CategoryExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new LinkedHashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            CategoryErrorCode categoryErrorCode = CategoryErrorCode.fromName(fieldError.getDefaultMessage());
            errors.put(fieldError.getField(), categoryErrorCode.getMessage());
        }

        SystemCode errorCode = SystemCode.VALIDATION_ERROR;
        return ApiResponse.buildFailResponse(errorCode.getCode(), errorCode.getMessage(), errors);
    }

    @ExceptionHandler(value = {CategoryNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return ApiResponse.buildFailResponse(e);
    }

    @ExceptionHandler(value = {CategoryAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleCategoryAlreadyExistException(CategoryAlreadyExistException e) {
        return ApiResponse.buildFailResponse(e);
    }

    @ExceptionHandler(value = {CategoryCanNotMove.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleCategoryCanNotMove(CategoryCanNotMove e) {
        return ApiResponse.buildFailResponse(e);
    }
}
