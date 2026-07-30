package com.mshop.app.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshop.app.common.core.exception.ErrorCode;
import com.mshop.app.common.core.exception.SystemCode;
import com.mshop.app.common.core.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Configuration
public class OAuth2ErrorConfig {

    private final ObjectMapper objectMapper;

    public OAuth2ErrorConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Custom authentication entry point for 401 errors.
    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return (request, response, authException) -> {
            // standard header
            new BearerTokenAuthenticationEntryPoint().commence(request, response, authException);

            writeErrorResponse(
                    response,
                    HttpStatus.UNAUTHORIZED,
                    SystemCode.UNAUTHORIZED,
                    request.getRequestURI()
            );
        };
    }

    // Custom access denied handler for 403 errors.
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // standard headers
            new BearerTokenAccessDeniedHandler().handle(request, response, accessDeniedException);

            writeErrorResponse(
                    response,
                    HttpStatus.FORBIDDEN,
                    SystemCode.FORBIDDEN,
                    request.getRequestURI()
            );
        };
    }

    private void writeErrorResponse(
            HttpServletResponse response,
            HttpStatus status,
            ErrorCode errorCode,
            String path) throws IOException {

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorBody = Map.of(
                "timestamp", Instant.now().toString(),
                "path", path
        );

        ApiResponse<Void> apiResponse = ApiResponse.buildFailResponse(errorCode.getCode(),
                errorCode.getMessage(), errorBody);

        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
}
