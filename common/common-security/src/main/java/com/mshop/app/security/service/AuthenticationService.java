package com.mshop.app.security.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public Jwt getCurrentJwt() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getToken();
        }
        return null;
    }

    // Extracts the subject (user ID) from the current JWT.
    public String getCurrentUserId() {
        Jwt jwt = getCurrentJwt();
        return jwt != null ? jwt.getSubject() : null;
    }

    //get a custom claim from the JWT.
    @SuppressWarnings("unchecked")
    public <T> T getClaim(String claimName, Class<T> claimType) {
        Jwt jwt = getCurrentJwt();
        if (jwt == null) {
            return null;
        }

        Object claim = jwt.getClaim(claimName);
        if (claimType.isInstance(claim)) {
            return (T) claim;
        }
        return null;
    }
}
