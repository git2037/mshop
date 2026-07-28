package com.mshop.app.user.repository;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    User create(User user);

    List<User> findAll(Query query);

    User update(String userId, User user);

    boolean existsActiveUserById(String userId);

    Optional<User> findById(String userId);

    User update(User user);

    Set<String> findKeycloakIdIn(List<String> keycloakIds);
}
