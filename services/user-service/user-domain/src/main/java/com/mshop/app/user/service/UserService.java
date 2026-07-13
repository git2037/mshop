package com.mshop.app.user.service;

import com.mshop.app.user.model.User;

public interface UserService {
    User createProfile(User user);
    User getUserProfile(String email);
}
