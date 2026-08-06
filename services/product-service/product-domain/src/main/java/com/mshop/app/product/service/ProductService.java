package com.mshop.app.product.service;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Product create(Product product, Set<String> categoryIds);

    List<Product> getAll(Query query);

    List<Product> getAllEnableProduct(Query query);

    Product getById(String id);

    Product getEnableProductById(String id);
}
