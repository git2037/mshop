package com.mshop.app.product.service;

import com.mshop.app.product.model.AttributeValue;

import java.util.List;
import java.util.Set;

public interface ProductAttributeService {
    List<AttributeValue> getAllEnabledAttributeValuesByProductId(String productId);

    List<AttributeValue> getAllAttributeValuesByProductId(String productId);

    void attachAttributeValuesToProduct(String productId, Set<String> attributeValueIds);

    void detachAttributeValueFromProduct(String productId, Set<String> attributeValueIds);
}
