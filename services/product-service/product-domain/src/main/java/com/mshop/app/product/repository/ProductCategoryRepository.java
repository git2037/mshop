package com.mshop.app.product.repository;

import java.util.List;
import java.util.Set;

public interface ProductCategoryRepository {
    void saveAll(String productId, List<String> categoryIds);

    void removeAll(String productId, Set<String> categoryIds);

    Set<String> findAllCategoryIdsByProductId(String productId);
}
