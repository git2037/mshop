package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.SkuAttributeValueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuAttributeValueJPARepository extends JpaRepository<SkuAttributeValueEntity, String> {
}
