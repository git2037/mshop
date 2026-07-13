package com.mshop.app.user.repository;

import com.mshop.app.user.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    User create(User user);
}
