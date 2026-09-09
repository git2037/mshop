package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.AttributeValueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AttributeValueJPARepository extends JpaRepository<AttributeValueEntity, String>, JpaSpecificationExecutor<AttributeValueEntity> {
    Page<AttributeValueEntity> findAllByAttributeCode(String attributeCode, Pageable pageable);

    Set<AttributeValueEntity> findAllByIdIn(Set<String> attributeValueIds);

    @Query("""
            select av
            from AttributeValueEntity av, ProductAttributeValueEntity pav
            where pav.productId = :productId
              and av.id = pav.attributeValueId
            """)
    List<AttributeValueEntity> findAllInProductAttributeValueByProductId(
            @Param("productId") String productId);

    @Query("""
            select av
            from AttributeValueEntity av,
                 ProductAttributeValueEntity pav,
                 AttributeEntity a
            where pav.productId = :productId
              and av.id = pav.attributeValueId
              and a.code = av.attributeCode
              and a.deleted is null
              and av.deleted is null
            """)
    List<AttributeValueEntity> findAllEnableAttributeValueInProductAttributeValueByProductId(
            @Param("productId") String productId);
}
