package com.mshop.app.product.service.impl;

import com.mshop.app.common.core.exception.BadRequestException;
import com.mshop.app.common.core.exception.ResourceNotFoundException;
import com.mshop.app.common.core.response.BaseDto;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.model.Sku;
import com.mshop.app.product.model.SkuAttributeValue;
import com.mshop.app.product.reader.ProductReader;
import com.mshop.app.product.repository.AttributeValueRepository;
import com.mshop.app.product.repository.SkuAttributeValueRepository;
import com.mshop.app.product.repository.SkuRepository;
import com.mshop.app.product.service.SkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkuServiceImpl implements SkuService {

    private final SkuRepository skuRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final ProductReader productReader;
    private final SkuAttributeValueRepository skuAttributeValueRepository;

    private static final String DASH_SPLIT = "-";

    @Override
    @Transactional
    public Sku createSku(Sku sku, Set<String> attributeValueIds) {

        Product product = productReader.findById(sku.getProductId());

        Set<AttributeValue> attributeValueList = attributeValueRepository
                .findAllByIdIn(attributeValueIds);

        validateAttributeValue(attributeValueList, attributeValueIds);

        sku.setCode(generateSkuCode(product.getCode(), attributeValueList));

        log.info("Create sku : {}", sku);
        Sku createdSku = skuRepository.save(sku);

        String createdSkuId = createdSku.getId();
        log.info("Save sku[id={}], attribute value ids={}", createdSkuId, attributeValueIds);
        skuAttributeValueRepository.saveAll(createdSkuId, attributeValueIds);

        return createdSku;
    }

    @Override
    public List<SkuAttributeValue> getAllByProductId(String productId) {
        productReader.existById(productId);

        return skuRepository.findAllByProductId(productId);
    }

    @Override
    public List<SkuAttributeValue> getAllEnableSkuByProductId(String productId) {
        productReader.existById(productId);

        return skuRepository.findAllByProductIdAndDeletedIsNull(productId);
    }

    @Override
    public List<SkuAttributeValue> getById(String id) {
        if (!skuRepository.existsById(id)) {
            log.warn("Sku [id={}] not found]", id);
            throw new ResourceNotFoundException(ProductServiceCode.SKU_NOT_FOUND);
        }

        return skuRepository.findById(id);
    }

    private void validateAttributeValue(Set<AttributeValue> attributeValueList, Set<String> attributeValueIds) {
        validateNotFoundAttributeValue(attributeValueList, attributeValueIds);
        validateDuplicateAttributeCode(attributeValueList);
    }

    private void validateDuplicateAttributeCode(Set<AttributeValue> attributeValueList) {
        Set<String> attributeCodes = new HashSet<>();
        List<String> duplicatedAttributeCodes = new ArrayList<>();

        attributeValueList.forEach(attributeValue -> {
            String attributeCode = attributeValue.getAttributeCode();
            if (attributeCodes.contains(attributeCode)) {
                duplicatedAttributeCodes.add(attributeCode);
            } else {
                attributeCodes.add(attributeCode);
            }
        });

        if (!duplicatedAttributeCodes.isEmpty()) {
            log.warn("Duplicated attribute codes : {}", duplicatedAttributeCodes);
            throw new BadRequestException(ProductServiceCode.SKU_DUPLICATED_ATTRIBUTE_CODE,
                    "Duplicated attribute codes: " + duplicatedAttributeCodes);
        }
    }

    private void validateNotFoundAttributeValue(Set<AttributeValue> attributeValueList, Set<String> attributeValueIds) {
        if (attributeValueIds.size() != attributeValueList.size()) {
            Set<String> notExistAttributeValueIds = new HashSet<>(attributeValueIds);
            Set<String> existAttributeValueIds = attributeValueList.stream()
                    .map(BaseDto::getId)
                    .collect(Collectors.toSet());
            notExistAttributeValueIds.removeAll(existAttributeValueIds);

            log.warn("{} not found", notExistAttributeValueIds);
            throw new ResourceNotFoundException(ProductServiceCode.ATTRIBUTE_VALUE_NOT_FOUND,
                    "Attribute value with ids = %s not found".formatted(notExistAttributeValueIds));
        }
    }

    private String generateSkuCode(String productCode, Set<AttributeValue> attributeValueList) {
        StringBuilder builder = new StringBuilder();
        builder.append(productCode);

        attributeValueList.stream()
                .sorted(Comparator.comparing(AttributeValue::getAttributeCode))
                .map(AttributeValue::getValueCode)
                .forEachOrdered(code -> builder.append(DASH_SPLIT).append(code));

        return builder.toString();
    }
}
