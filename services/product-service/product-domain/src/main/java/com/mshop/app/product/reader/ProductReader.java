package com.mshop.app.product.reader;

import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.product.ProductNotFoundException;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductReader {

    private final ProductRepository productRepository;

    public void existById(String id) {
        if (!productRepository.existById(id)) {
            throw productNotFoundException(id);
        }
    }

    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> productNotFoundException(id));
    }

    public Product findEnableProductById(String id) {
        return productRepository.findByIdAndDeletedIsNull(id)
                .orElseThrow(() -> productNotFoundException(id));
    }

    private ProductNotFoundException productNotFoundException(String productId) {
        log.warn("Product [id={}] not found]", productId);
        return new ProductNotFoundException(ProductServiceCode.PRODUCT_NOT_FOUND);
    }
}
