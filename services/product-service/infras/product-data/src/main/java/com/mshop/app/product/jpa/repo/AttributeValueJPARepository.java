package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.AttributeValueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttributeValueJPARepository extends JpaRepository<AttributeValueEntity, String>, JpaSpecificationExecutor<AttributeValueEntity> {
    Page<AttributeValueEntity> findAllByAttributeCode(String attributeCode, Pageable pageable);
}
