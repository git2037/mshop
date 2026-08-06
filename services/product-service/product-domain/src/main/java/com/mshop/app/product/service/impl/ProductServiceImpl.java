package com.mshop.app.product.service.impl;

import com.mshop.app.ProductCode;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.exception.ProductNotFoundException;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.repository.ProductRepository;
import com.mshop.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product create(Product product, Set<String> categoryIds) {
        log.info("Create product[name={}]", product.getName());
        return productRepository.create(product);
    }

    @Override
    public List<Product> getAll(Query query) {
        return productRepository.findAll(query);
    }

    @Override
    public List<Product> getAllEnableProduct(Query query) {
        return productRepository.findAllEnableProduct(query);
    }

    @Override
    public Product getById(String id) {
        return findById(id);
    }

    @Override
    public Product getEnableProductById(String id) {
        return findEnableProductById(id);
    }

    private Product findById(String id) {
        return findProduct(id,
                () -> productRepository.findById(id));
    }

    private Product findEnableProductById(String id) {
        return findProduct(id,
                () -> productRepository.findByIdAndDeletedIsNull(id));
    }

    private Product findProduct(String productId, Supplier<Optional<Product>> supplier) {
        return supplier.get().orElseThrow(
                () -> {
                    log.warn("Product [id={}] not found]", productId);
                    return new ProductNotFoundException(ProductCode.PRODUCT_NOT_FOUND);
                });
    }
}
