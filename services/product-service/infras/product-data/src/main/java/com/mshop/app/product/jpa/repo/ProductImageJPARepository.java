package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProductImageJPARepository extends JpaRepository<ProductImageEntity, String> {

    @Query(value = """
            select fileName
            from ProductImageEntity
            where productId = :productId
            and fileName in :fileNames
            """)
    Set<String> findAllFileNamesByProductIdAndFileNameIn(@Param("productId") String productId,
                                                         @Param("fileNames") Set<String> fileNames);

    @Modifying
    @Query(value = """
            delete from ProductImageEntity
            where productId = :productId
            and fileName in :fileNames
            """)
    int removeAllByProductIdAndFileNameIn(@Param("productId") String productId,
                                          @Param("fileNames") Set<String> fileNames);

    @Query(value = """
            select fileName
            from ProductImageEntity
            where productId = :productId
            """)
    Set<String> findAllFileNamesByProductId(@Param("productId")String productId);
}
