package com.mshop.app.product.repository;

import com.mshop.app.common.core.exception.ConflictException;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.jpa.entity.SkuAttributeValueEntity;
import com.mshop.app.product.jpa.repo.SkuAttributeValueJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SkuAttributeValueRepositoryImpl implements SkuAttributeValueRepository {

    private final SkuAttributeValueJPARepository  skuAttributeValueJPARepository;

    @Override
    @Transactional
    public void saveAll(String skuId, Set<String> attributeValueIds) {
        List<SkuAttributeValueEntity> entities = attributeValueIds.stream()
                .map(attributeValueId -> SkuAttributeValueEntity.builder()
                        .skuId(skuId)
                        .attributeValueId(attributeValueId)
                        .build())
                .collect(Collectors.toUnmodifiableList());

        try {
            skuAttributeValueJPARepository.saveAllAndFlush(entities);
        } catch (DataIntegrityViolationException ex) {
            log.error("Duplicate attribute value in sku[id={}]", skuId, ex);
            throw new ConflictException(ProductServiceCode.SKU_DUPLICATE_ATTRIBUTE_VALUE_IN_SKU);
        }
    }
}
