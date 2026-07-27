package com.mshop.app.user.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Builder
@Getter
public class KeycloakHttpRequest {
    private HttpMethod method;
    private String uri;
    private Object body;
}
