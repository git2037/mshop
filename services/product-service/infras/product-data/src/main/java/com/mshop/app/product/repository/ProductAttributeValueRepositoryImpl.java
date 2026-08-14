package com.mshop.app.product.repository;

import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.product.ProductAttributeValueAlreadyExistException;
import com.mshop.app.product.jpa.entity.ProductAttributeValueEntity;
import com.mshop.app.product.jpa.repo.ProductAttributeValueJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductAttributeValueRepositoryImpl implements ProductAttributeValueRepository {

    private final ProductAttributeValueJPARepository productAttributeValueJPARepository;

    @Override
    @Transactional
    public void createAllByProductIdAndAttributeValueIdsIn(String productId, Set<String> attributeValueIds) {
        List<ProductAttributeValueEntity> entities = attributeValueIds.stream()
                .map(attributeValueId -> ProductAttributeValueEntity.builder()
                        .productId(productId)
                        .attributeValueId(attributeValueId)
                        .build())
                .collect(Collectors.toUnmodifiableList());
        try {
            productAttributeValueJPARepository.saveAllAndFlush(entities);
        } catch (DataIntegrityViolationException exception) {
            log.error("Duplicate attribute value detected in product[id={}]", productId, exception);
            throw new ProductAttributeValueAlreadyExistException(ProductServiceCode.PRODUCT_ATTRIBUTE_VALUE_ALREADY_EXIST);
        }
    }

    @Override
    public Set<String> findAllAttributeCodesByProductIdAndAttributeCodeIn(String productId, Set<String> attributeCodes) {
        return productAttributeValueJPARepository.findAllAttributeCodesByProductIdAndAttributeCodeIn(productId, attributeCodes);
    }

    @Override
    @Transactional
    public void removeAllByProductIdAndAttributeValueIdsIn(String productId, Set<String> attributeValueIds) {
        int affectedRows = productAttributeValueJPARepository.removeAllByProductIdAndAttributeValueIdsIn(productId, attributeValueIds);
        log.info("Removed {} rows", affectedRows);
    }

    @Override
    public Set<String> findAllAttributeValueIdsByProductIdAndAttributeValueIdIn(String productId, Set<String> attributeValueIds) {
        return productAttributeValueJPARepository.findAllAttributeValueIdsByProductIdAndAttributeValueIdIn(productId, attributeValueIds);
    }
}
