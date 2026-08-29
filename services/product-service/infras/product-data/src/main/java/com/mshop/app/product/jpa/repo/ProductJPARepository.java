package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductJPARepository extends JpaRepository<ProductEntity, String>, JpaSpecificationExecutor<ProductEntity> {
    Optional<ProductEntity> findByIdAndDeletedIsNull(String id);
}
