package com.mshop.app.user.repository;

import com.mshop.app.user.model.UserJob;

import java.util.Optional;

public interface UserJobRepository {
    Optional<UserJob> findById(String id);

    void updateLastProcessedTime(UserJob userJob);
}
