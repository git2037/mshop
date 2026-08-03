package com.mshop.app.product.repository;

import com.mshop.app.product.model.Product;

public interface ProductRepository {

    Product create(Product product);
}