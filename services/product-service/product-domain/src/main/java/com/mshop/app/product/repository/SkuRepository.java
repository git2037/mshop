package com.mshop.app.product.repository;

import com.mshop.app.product.model.Sku;
import com.mshop.app.product.model.SkuAttributeValue;

import java.util.List;

public interface SkuRepository {

    Sku save(Sku sku);

    List<SkuAttributeValue> findAllByProductId(String productId);

    List<SkuAttributeValue> findAllByProductIdAndDeletedIsNull(String productId);

    List<SkuAttributeValue> findById(String id);

    boolean existsById(String id);
}