package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.AttributeValueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AttributeValueJPARepository extends JpaRepository<AttributeValueEntity, String>, JpaSpecificationExecutor<AttributeValueEntity> {
    Page<AttributeValueEntity> findAllByAttributeCode(String attributeCode, Pageable pageable);

    @Modifying
    @Query(value = """
            update AttributeValueEntity
            set deleted = current_timestamp(), updatedAt = current_timestamp()
            where attributeCode = :code
            """)
    int disableAllByAttributeCode(@Param("code") String code);

    @Modifying
    @Query(value = """
            update AttributeValueEntity
            set deleted = null, updatedAt = current_timestamp()
            where attributeCode = :code
            """)
    int enableAllByAttributeCode(@Param("code") String code);
}
