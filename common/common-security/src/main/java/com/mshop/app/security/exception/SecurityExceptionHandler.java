package com.mshop.app.security.exception;

import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.exception.SystemCode;
import com.mshop.app.common.core.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
@Order(1)
public class SecurityExceptionHandler {

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Void> handleAuthDenied(AuthorizationDeniedException ex,
                                              HttpServletRequest request) {
        ErrorCode errorCode = SystemCode.FORBIDDEN;

        Map<String, Object> errorBody = Map.of(
                "timestamp", Instant.now().toString(),
                "path", request.getRequestURI()
        );

        return ApiResponse.buildFailResponse(errorCode.getCode(),
                errorCode.getMessage(), errorBody);
    }
}
