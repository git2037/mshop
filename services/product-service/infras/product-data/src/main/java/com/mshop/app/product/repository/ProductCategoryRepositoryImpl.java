package com.mshop.app.product.repository;

import com.mshop.app.product.jpa.entity.ProductCategoryEntity;
import com.mshop.app.product.jpa.repo.ProductCategoryJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public void saveAll(String productId, Set<String> categoryIds) {
        List<ProductCategoryEntity> productCategoryEntities = categoryIds.stream()
                .map(categoryId -> ProductCategoryEntity.builder()
                        .productId(productId)
                        .categoryId(categoryId)
                        .build())
                .collect(Collectors.toList());

        productCategoryJPARepository.saveAll(productCategoryEntities);
    }
}
