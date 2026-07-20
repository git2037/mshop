package com.mshop.app.user.service;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.user.model.User;

import java.util.List;

public interface UserService {
    User createProfile(User user);

    User getUserProfile(String email);

    List<User> findAll(Query request);

    User updateProfile(String userId, User user);

    User findById(String id);

    void disableUser(String id);

    void enableUser(String id);
}
