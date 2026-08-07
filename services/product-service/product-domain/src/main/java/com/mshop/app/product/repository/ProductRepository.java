package com.mshop.app.product.repository;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    List<Product> findAll(Query query);

    List<Product> findAllEnableProduct(Query query);

    Optional<Product> findById(String id);

    Optional<Product> findByIdAndDeletedIsNull(String id);

    boolean existById(String id);

    void disable(String productId);

    void enable(String productId);
}