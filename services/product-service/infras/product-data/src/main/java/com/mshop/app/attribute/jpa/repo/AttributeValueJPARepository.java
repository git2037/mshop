package com.mshop.app.attribute.jpa.repo;

import com.mshop.app.attribute.jpa.entity.AttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttributeValueJPARepository extends JpaRepository<AttributeValueEntity, String>, JpaSpecificationExecutor<AttributeValueEntity> {
}
