package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProductCategoryJPARepository extends JpaRepository<ProductCategoryEntity, String> {

    @Query("""
            select categoryId
            from ProductCategoryEntity
            where productId = :productId
            """)
    Set<String> findAllCategoryIdsByProductId(@Param("productId") String productId);

    @Modifying
    @Query(value = """
            delete
            from ProductCategoryEntity
            where productId=:productId and categoryId in :categoryIds
            """)
    int deleteAllByProductIdAndCategoryIds(@Param("productId") String productId, @Param("categoryIds") Set<String> categoryIds);
}
