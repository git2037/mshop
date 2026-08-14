package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.ProductAttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProductAttributeValueJPARepository extends JpaRepository<ProductAttributeValueEntity, String>, JpaSpecificationExecutor<ProductAttributeValueEntity> {

    @Query(value = """
            select av.attributeCode
            from AttributeValueEntity av, ProductAttributeValueEntity pav
            where pav.productId = :productId
            and av.id = pav.attributeValueId
            and av.attributeCode in :codes
            """)
    Set<String> findAllAttributeCodesByProductIdAndAttributeCodeIn(@Param("productId") String productId, @Param("codes") Set<String> attributeCodes);

    @Modifying
    @Query(value = """
            delete from ProductAttributeValueEntity
            where productId = :productId
            and attributeValueId in :ids
            """)
    int removeAllByProductIdAndAttributeValueIdsIn(@Param("productId") String productId, @Param("ids") Set<String> attributeValueIds);

    @Query(value = """
            select attributeValueId
            from ProductAttributeValueEntity
            where productId = :productId
            and attributeValueId in :ids
            """)
    Set<String> findAllAttributeValueIdsByProductIdAndAttributeValueIdIn(@Param("productId") String productId, @Param("ids") Set<String> attributeValueIds);
}
