package com.mshop.app.product.repository;

import java.util.Set;

public interface ProductAttributeValueRepository {
    void createAllByProductIdAndAttributeValueIdsIn(String productId, Set<String> attributeValueIds);

    Set<String> findAllAttributeCodesByProductIdAndAttributeCodeIn(String productId, Set<String> attributeCodes);

    void removeAllByProductIdAndAttributeValueIdsIn(String productId, Set<String> attributeValueIds);

    Set<String> findAllAttributeValueIdsByProductIdAndAttributeValueIdIn(String productId, Set<String> attributeValueIds);
}
