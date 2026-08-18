package com.mshop.app.product.service.impl;

import com.mshop.app.common.core.exception.BadRequestException;
import com.mshop.app.common.core.exception.ConflictException;
import com.mshop.app.common.core.exception.ResourceNotFoundException;
import com.mshop.app.common.core.response.BaseDto;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.attribute.AttributeValueNotFoundException;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.reader.ProductReader;
import com.mshop.app.product.repository.AttributeValueRepository;
import com.mshop.app.product.repository.ProductAttributeValueRepository;
import com.mshop.app.product.service.ProductAttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private final ProductReader productReader;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final AttributeValueRepository attributeValueRepository;

    @Override
    public List<AttributeValue> getAllEnabledAttributeValuesByProductId(String productId) {
        return attributeValueRepository.findAllEnableAttributeValueInProductAttributeValueByProductId(productId);
    }

    @Override
    public List<AttributeValue> getAllAttributeValuesByProductId(String productId) {
        return attributeValueRepository.findAllInProductAttributeValueByProductId(productId);
    }

    @Override
    @Transactional
    public void attachAttributeValuesToProduct(String productId, Set<String> attributeValueIds) {
        productReader.existById(productId);
        validateAttributeValueIds(productId, attributeValueIds);

        log.info("Add attribute values with id={} to product[id={}]", attributeValueIds, productId);
        productAttributeValueRepository.createAllByProductIdAndAttributeValueIdsIn(productId, attributeValueIds);
    }

    @Override
    @Transactional
    public void detachAttributeValueFromProduct(String productId, Set<String> attributeValueIds) {
        productReader.existById(productId);

        Set<AttributeValue> attributeValues = attributeValueRepository.findAllByIdIn(attributeValueIds);
        validateAttributeValueIdsNotFound(attributeValues, attributeValueIds);

        validateAttributeValueIdNotFoundInProduct(productId, attributeValueIds);
        Set<String> attributeCodes = attributeValues.stream()
                .map(AttributeValue::getAttributeCode)
                .collect(Collectors.toSet());
        log.info("Remove attribute with code={} from product[id={}]", attributeCodes, productId);
        productAttributeValueRepository.removeAllByProductIdAndAttributeValueIdsIn(productId, attributeValueIds);
    }

    private void validateAttributeValueIdNotFoundInProduct(String productId, Set<String> attributeValueIds) {
        Set<String> attributeValueIdsInProduct = productAttributeValueRepository
                .findAllAttributeValueIdsByProductIdAndAttributeValueIdIn(productId, attributeValueIds);

        if (attributeValueIdsInProduct.size() != attributeValueIds.size()) {
            Set<String> missingAttributeValues = new HashSet<>(attributeValueIds);
            missingAttributeValues.removeAll(attributeValueIdsInProduct);

            log.error("Attribute value with ids={} not found in product[id={}]", missingAttributeValues, productId);
            throw new ResourceNotFoundException(ProductServiceCode.ATTRIBUTE_VALUE_NOT_FOUND,
                    MessageFormat.format("Attribute value with ids={0} not found in this product", missingAttributeValues));
        }
    }

    private void validateAttributeValueIds(String productId, Set<String> attributeValueIds) {
        if (CollectionUtils.isEmpty(attributeValueIds))
            return;

        Set<AttributeValue> attributeValues = attributeValueRepository.findAllByIdIn(attributeValueIds);
        validateAttributeValueIdsNotFound(attributeValues, attributeValueIds);
        validateDuplicateAttributeCode(attributeValues);
        validateDuplicateAttributeCodeInProduct(productId, attributeValues);
    }

    private void validateDuplicateAttributeCodeInProduct(String productId, Set<AttributeValue> attributeValues) {
        Set<String> attributeCodes = attributeValues.stream()
                .map(AttributeValue::getAttributeCode)
                .collect(Collectors.toSet());
        Set<String> existingAttributeCodes = productAttributeValueRepository
                .findAllAttributeCodesByProductIdAndAttributeCodeIn(productId, attributeCodes);

        if (!CollectionUtils.isEmpty(existingAttributeCodes)) {
            log.error("Attribute with codes={} already exist in product[id={}]", existingAttributeCodes, productId);
            throw new ConflictException(ProductServiceCode.ATTRIBUTE_CODE_ALREADY_EXIST_IN_PRODUCT,
                    MessageFormat.format("Attribute with codes={0} already exist in this product", existingAttributeCodes));
        }
    }

    private void validateDuplicateAttributeCode(Set<AttributeValue> attributeValues) {
        Map<String, String> attributeCodeMap = new HashMap<>();
        for (AttributeValue attributeValue : attributeValues) {
            String attributeCode = attributeValue.getAttributeCode();
            String attributeValueId = attributeValue.getId();
            if (!attributeCodeMap.containsKey(attributeCode)) {
                attributeCodeMap.put(attributeCode, attributeValueId);
            } else {
                String attributeValueIdInMap = attributeCodeMap.get(attributeCode);
                log.error("Duplicate attribute code={} by attribute value[id={}] and attribute value[id={}]",
                        attributeCode, attributeValueId, attributeValueIdInMap);
                throw new BadRequestException(ProductServiceCode.DUPLICATED_ATTRIBUTE_CODE,
                        MessageFormat.format("Duplicate attribute code={0} in this request", attributeCode));
            }
        }
    }

    private void validateAttributeValueIdsNotFound(Set<AttributeValue> attributeValues, Set<String> attributeValueIds) {
        Set<String> attributeValueIdsInDB = attributeValues.stream()
                .map(BaseDto::getId)
                .collect(Collectors.toSet());
        if (attributeValueIdsInDB.size() != attributeValueIds.size()) {
            Set<String> missingIds = new HashSet<>(attributeValueIds);
            missingIds.removeAll(attributeValueIdsInDB);

            throw new AttributeValueNotFoundException(
                    ProductServiceCode.ATTRIBUTE_VALUE_NOT_FOUND,
                    "Attribute values with id=" + missingIds + " not found"
            );
        }
    }
}
