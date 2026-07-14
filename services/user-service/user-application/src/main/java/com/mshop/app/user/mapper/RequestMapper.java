package com.mshop.app.user.mapper;

import com.mshop.app.user.model.KeycloakAccount;
import com.mshop.app.user.model.User;
import com.mshop.app.user.request.UserCreationRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {
    public User toUser(UserCreationRequest request) {
        return User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public KeycloakAccount toAccunt(UserCreationRequest request) {
        return KeycloakAccount.builder()
                .email(request.getEmail())
                .password(request.getPassword()).build();
    }
}
