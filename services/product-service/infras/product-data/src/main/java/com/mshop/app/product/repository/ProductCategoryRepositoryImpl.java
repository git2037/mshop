package com.mshop.app.product.repository;

import com.mshop.app.product.jpa.entity.ProductCategoryEntity;
import com.mshop.app.product.jpa.repo.ProductCategoryJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    private final ProductCategoryJPARepository productCategoryJPARepository;

    @Override
    @Transactional
    public void saveAll(String productId, List<String> categoryIds) {
        List<ProductCategoryEntity> productCategoryEntities = from(productId, categoryIds);
        productCategoryJPARepository.saveAll(productCategoryEntities);
    }

    @Override
    @Transactional
    public void removeAll(String productId, Set<String> categoryIds) {
        int affectedRows = productCategoryJPARepository.deleteAllByProductIdAndCategoryIds(productId, categoryIds);
        log.info("Affected rows: {}", affectedRows);
    }

    @Override
    public Set<String> findAllCategoryIdsByProductId(String productId) {
        return productCategoryJPARepository.findAllCategoryIdsByProductId(productId);
    }

    private List<ProductCategoryEntity> from(String productId, Collection<String> categoryIds) {
        return categoryIds.stream()
                .map(categoryId -> ProductCategoryEntity.builder()
                        .productId(productId)
                        .categoryId(categoryId)
                        .build())
                .collect(Collectors.toList());
    }
}
