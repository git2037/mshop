package com.mshop.app.user.service.impl;

import com.mshop.app.common.core.exception.SystemCode;
import com.mshop.app.common.core.exception.SystemException;
import com.mshop.app.user.model.KeycloakAccount;
import com.mshop.app.user.model.User;
import com.mshop.app.user.repository.KeycloakRepository;
import com.mshop.app.user.service.AuthService;
import com.mshop.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final KeycloakRepository keycloakRepository;
    private final UserService userService;

    @Override
    public User register(User user, KeycloakAccount account) {
        String keycloakId = keycloakRepository.createAccount(account)
                .orElseThrow(() -> {
                            log.error("Create account failed. Can't get keycloak id");
                            return new SystemException(SystemCode.UNEXPECTED_ERROR);
                        }
                );

        log.info("User created successfully id={}", keycloakId);

        user.setKeycloakId(keycloakId);
        log.info("Save user profile to DB");
        User userFromDB = userService.createProfile(user);
        log.info("Save user profile successfully");
        return userFromDB;
    }
}
