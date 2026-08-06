package com.mshop.app.product.repository;

import java.util.Set;

public interface ProductCategoryRepository {
    void saveAll(String productId, Set<String> categoryIds);
}
