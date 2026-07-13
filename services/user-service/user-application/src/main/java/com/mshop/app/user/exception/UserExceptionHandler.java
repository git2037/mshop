package com.mshop.app.user.exception;

import com.mshop.app.common.core.exception.SystemCode;
import com.mshop.app.common.core.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
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
public class UserExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new LinkedHashMap<>();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            UserCode userCode = UserCode.fromName(fieldError.getDefaultMessage());
            String message = userCode.getMessage();

            if (userCode == UserCode.PASSWORD_MIN_LENGTH) {
                ConstraintViolation<?> violation = fieldError.unwrap(ConstraintViolation.class);
                String minValue = String.valueOf(violation.getConstraintDescriptor().getAttributes().get(MIN_ATTRIBUTE));
                message = message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
            }

            errors.put(fieldError.getField(), message);
        }

        SystemCode errorCode = SystemCode.VALIDATION_ERROR;
        return ApiResponse.buildFailResponse(errorCode.getCode(), errorCode.getMessage(), errors);
    }

    @ExceptionHandler(value = {KeycloakConfigurationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleKeycloakConfigurationException(KeycloakConfigurationException e) {
        log.error("Keycloak configuration failure!", e);
        return ApiResponse.buildFailResponse(e);
    }
}
