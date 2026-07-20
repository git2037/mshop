package com.mshop.app.user.repository;

import com.mshop.app.user.model.KeycloakAccount;

import java.util.Optional;

public interface KeycloakRepository {
    Optional<String> createAccount(KeycloakAccount account);

    void deleteAccount(String id);

    void disableAccount(String keycloakId);
}
