package com.mshop.app.user.jpa.repo;

import com.mshop.app.user.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);
}