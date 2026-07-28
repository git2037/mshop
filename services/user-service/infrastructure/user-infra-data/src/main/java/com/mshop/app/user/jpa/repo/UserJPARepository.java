package com.mshop.app.user.jpa.repo;

import com.mshop.app.user.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdAndDeletedIsNull(String userId);

    boolean existsByIdAndDeletedIsNull(String userId);

    List<UserEntity> findByKeycloakIdIn(List<String> keycloakIds);
}