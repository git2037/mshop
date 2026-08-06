package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryJPARepository extends JpaRepository<ProductCategoryEntity, String> {
}
