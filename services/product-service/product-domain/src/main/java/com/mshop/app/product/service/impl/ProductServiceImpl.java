package com.mshop.app.product.service.impl;

import com.mshop.app.ProductServiceCode;
import com.mshop.app.category.exception.CategoryNotLeafException;
import com.mshop.app.category.repository.CategoryRepository;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.exception.ProductNotFoundException;
import com.mshop.app.product.mapper.ProductDomainMapper;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.repository.ProductCategoryRepository;
import com.mshop.app.product.repository.ProductRepository;
import com.mshop.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductDomainMapper productDomainMapper;

    @Override
    @Transactional
    public Product create(Product product) {
        log.info("Create product[name={}, description={}]",
                product.getName(), StringUtils.abbreviate(product.getDescription(), 15));
        return productRepository.save(product);
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

    @Override
    public Product update(Product product) {
        String productId = product.getId();
        Product productDB = findById(productId);
        productDomainMapper.updateProductFromDto(product, productDB);
        log.info("Update product[id={}, name={}, description={}]", productId, product.getName(),
                StringUtils.abbreviate(product.getDescription(), 15));
        return productRepository.save(productDB);
    }

    @Override
    public void addToCategories(String productId, Set<String> categoryIds) {
        existById(productId);
        validateCategories(categoryIds);

        Set<String> existingCategoryIds = productCategoryRepository
                .findAllCategoryIdsByProductId(productId);

        List<String> toSave = categoryIds.stream()
                .filter(id -> !existingCategoryIds.contains(id))
                .toList();


        log.info("Save product to categoryIds={}", toSave);
        productCategoryRepository.saveAll(productId, toSave);
    }

    @Override
    public void removeFromCategories(String productId, Set<String> categoryIds) {
        existById(productId);
        validateCategories(categoryIds);

        log.info("Remove product from categoryIds={}", categoryIds);
        productCategoryRepository.removeAll(productId, categoryIds);
    }

    @Override
    public void disable(String productId) {
        Product product = findById(productId);

        if (product.getDeleted() != null){
            log.info("Product[id={}] already disabled", product.getId());
            return;
        }

        log.info("Disable product[id={}]", productId);
        productRepository.disable(productId);
    }

    @Override
    public void enable(String productId) {
        Product product = findById(productId);

        if (product.getDeleted() == null){
            log.info("Product[id={}] already enabled", product.getId());
            return;
        }

        log.info("Enable product[id={}]", productId);
        productRepository.enable(productId);
    }

    private void validateCategories(Set<String> categoryIds) {
        if (CollectionUtils.isEmpty(categoryIds)) {
            return;
        }

        Set<String> leafNodeIds = categoryRepository.findLeafNodes(categoryIds);

        if (categoryIds.size() != leafNodeIds.size()) {
            Set<String> missingIds = new HashSet<>(categoryIds);
            missingIds.removeAll(leafNodeIds);

            throw new CategoryNotLeafException(
                    ProductServiceCode.CATEGORY_IS_NOT_LEAF,
                    "Categories with id=" + missingIds + " are not leaf nodes"
            );
        }
    }

    private void existById(String id) {
        if(!productRepository.existById(id)) {
            throw productNotFoundException(id);
        }
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
                () -> productNotFoundException(productId));
    }

    private ProductNotFoundException productNotFoundException(String productId) {
        log.warn("Product [id={}] not found]", productId);
        return new ProductNotFoundException(ProductServiceCode.PRODUCT_NOT_FOUND);
    }
}
