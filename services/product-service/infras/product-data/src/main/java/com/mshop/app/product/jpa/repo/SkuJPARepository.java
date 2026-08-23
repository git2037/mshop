package com.mshop.app.product.jpa.repo;

import com.mshop.app.product.jpa.entity.SkuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuJPARepository extends JpaRepository<SkuEntity, String> {
}
