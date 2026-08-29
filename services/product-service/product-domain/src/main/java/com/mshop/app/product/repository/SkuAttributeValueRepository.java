package com.mshop.app.product.repository;

import java.util.Set;

public interface SkuAttributeValueRepository {

    void saveAll(String skuId, Set<String>  attributeValueIds);
}