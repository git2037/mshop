package com.mshop.app.product.service.impl;

import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.category.CategoryNotLeafException;
import com.mshop.app.product.reader.ProductReader;
import com.mshop.app.product.repository.CategoryRepository;
import com.mshop.app.product.repository.ProductCategoryRepository;
import com.mshop.app.product.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ProductReader productReader;

    @Override
    @Transactional
    public void attachProduct(String productId, Set<String> categoryIds) {
        productReader.existById(productId);
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
    @Transactional
    public void detachProduct(String productId, Set<String> categoryIds) {
        productReader.existById(productId);
        validateCategories(categoryIds);

        log.info("Remove product from categoryIds={}", categoryIds);
        productCategoryRepository.removeAll(productId, categoryIds);
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
}
