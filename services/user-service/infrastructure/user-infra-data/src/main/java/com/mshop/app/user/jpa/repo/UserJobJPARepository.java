package com.mshop.app.user.jpa.repo;

import com.mshop.app.user.jpa.entity.UserJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJobJPARepository extends JpaRepository<UserJobEntity, String> {
}
