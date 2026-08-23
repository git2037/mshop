package com.mshop.app.product.service;

import com.mshop.app.product.model.Sku;

import java.util.Set;

public interface SkuService {

    Sku createSku(Sku sku, Set<String> attributeValueIds);
}
