package com.mshop.app.attribute.jpa.repo;

import com.mshop.app.attribute.jpa.entity.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttributeJPARepository extends JpaRepository<AttributeEntity, String>, JpaSpecificationExecutor<AttributeEntity> {
}
