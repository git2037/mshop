package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.SkuEntity;
import com.mshop.app.product.jpa.projection.SkuAttributeValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkuJPARepository extends JpaRepository<SkuEntity, String> {

    @Query(value = """
            select s.id     as skuId,
                   s.code   as skuCode,
                   s.stock  as stock,
                   s.price  as price,
                   a.code   as attributeCode,
                   a.name   as attributeName,
                   av.value as attributeValue
            from SkuAttributeValueEntity sav
                     join SkuEntity s on s.id = sav.skuId
                     join AttributeValueEntity av on sav.attributeValueId = av.id
                     join AttributeEntity a on av.attributeCode = a.code
            where s.productId = :productId
            """)
    List<SkuAttributeValueProjection> findAllByProductId(@Param("productId") String productId);

    @Query(value = """
            select s.id     as skuId,
                   s.code   as skuCode,
                   s.stock  as stock,
                   s.price  as price,
                   a.code   as attributeCode,
                   a.name   as attributeName,
                   av.value as attributeValue
            from SkuAttributeValueEntity sav
                     join SkuEntity s on s.id = sav.skuId
                     join AttributeValueEntity av on sav.attributeValueId = av.id
                     join AttributeEntity a on av.attributeCode = a.code
            where s.productId = :productId and s.deleted is null
            """)
    List<SkuAttributeValueProjection> findAllByProductIdAndDeletedIsNull(@Param("productId") String productId);

    @Query(value = """
            select s.id     as skuId,
                   s.code   as skuCode,
                   s.stock  as stock,
                   s.price  as price,
                   a.code   as attributeCode,
                   a.name   as attributeName,
                   av.value as attributeValue
            from SkuAttributeValueEntity sav
                     join SkuEntity s on s.id = sav.skuId
                     join AttributeValueEntity av on sav.attributeValueId = av.id
                     join AttributeEntity a on av.attributeCode = a.code
            where s.id = :id
            """)
    List<SkuAttributeValueProjection> findProjectionById(@Param("id") String id);
}
