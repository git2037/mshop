package com.mshop.app.user.service;

import com.mshop.app.user.model.KeycloakAccount;
import com.mshop.app.user.model.User;

public interface AuthService {
    User register(User user, KeycloakAccount account);
}
