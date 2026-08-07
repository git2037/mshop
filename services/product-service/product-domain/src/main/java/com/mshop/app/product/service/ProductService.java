package com.mshop.app.product.service;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Product create(Product product);

    List<Product> getAll(Query query);

    List<Product> getAllEnableProduct(Query query);

    Product getById(String id);

    Product getEnableProductById(String id);

    Product update(Product product);

    void addToCategories(String productId, Set<String> categoryIds);

    void removeFromCategories(String productId, Set<String> categoryIds);

    void disable(String productId);

    void enable(String productId);
}
