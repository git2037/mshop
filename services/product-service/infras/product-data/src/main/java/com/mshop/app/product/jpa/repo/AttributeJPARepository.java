package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AttributeJPARepository extends JpaRepository<AttributeEntity, String>, JpaSpecificationExecutor<AttributeEntity> {

    Optional<AttributeEntity> findByCode(String attributeCode);

    boolean existsByCode(String code);
}
