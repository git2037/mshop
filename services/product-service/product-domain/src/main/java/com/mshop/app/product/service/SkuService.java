package com.mshop.app.product.service;

import com.mshop.app.product.model.Sku;
import com.mshop.app.product.model.SkuAttributeValue;

import java.util.List;
import java.util.Set;

public interface SkuService {

    Sku createSku(Sku sku, Set<String> attributeValueIds);

    List<SkuAttributeValue> getAllByProductId(String productId);

    List<SkuAttributeValue> getAllEnableSkuByProductId(String productId);

    List<SkuAttributeValue> getById(String id);

    void disable(String skuId);

    void enable(String skuId);
}
