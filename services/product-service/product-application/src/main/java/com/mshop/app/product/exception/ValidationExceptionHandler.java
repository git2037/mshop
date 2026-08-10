package com.mshop.app.product.exception;

import com.mshop.app.product.constant.AttributeValueType;
import com.mshop.app.common.core.exception.SystemCode;
import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ValidationExceptionHandler {

    private static final String VALUES_PARAM = "values";

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new LinkedHashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            ProductServiceCode productServiceCode = ProductServiceCode.fromName(fieldError.getDefaultMessage());
            String fieldName = StringUtils.camelToSnake(fieldError.getField());
            errors.put(fieldName, createMessage(productServiceCode));
        }

        SystemCode errorCode = SystemCode.VALIDATION_ERROR;
        return ApiResponse.buildFailResponse(errorCode.getCode(), errorCode.getMessage(), errors);
    }

    private String createMessage(ProductServiceCode productServiceCode) {
        String message = productServiceCode.getMessage();
        if (productServiceCode.equals(ProductServiceCode.ATTRIBUTE_VALUE_INVALID)) {
            List<String> values = Arrays.stream(AttributeValueType.values())
                    .map(Enum::name)
                    .toList();
            message = message.replace("{" + VALUES_PARAM + "}", values.toString());
        }
        return message;
    }
}
