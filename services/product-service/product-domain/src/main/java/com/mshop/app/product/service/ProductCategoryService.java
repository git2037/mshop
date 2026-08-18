package com.mshop.app.product.service;

import java.util.Set;

public interface ProductCategoryService {
    void attachProduct(String productId, Set<String> categoryIds);

    void detachProduct(String productId, Set<String> categoryIds);
}
