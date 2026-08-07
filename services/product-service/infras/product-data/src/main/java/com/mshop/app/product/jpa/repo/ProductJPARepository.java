package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductJPARepository extends JpaRepository<ProductEntity, String>, JpaSpecificationExecutor<ProductEntity> {
    Optional<ProductEntity> findByIdAndDeletedIsNull(String id);

    @Modifying
    @Query(value = """
            update ProductEntity
            set deleted = current_timestamp(), updatedAt = current_timestamp()
            where id = :id
            """)
    void disable(@Param("id") String productId);

    @Modifying
    @Query(value = """
            update ProductEntity
            set deleted = null, updatedAt = current_timestamp()
            where id = :id
            """)
    void enable(@Param("id") String productId);
}
