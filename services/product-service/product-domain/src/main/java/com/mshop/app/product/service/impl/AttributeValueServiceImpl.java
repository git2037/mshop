package com.mshop.app.product.service.impl;

import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.attribute.AttributeAlreadyDisableException;
import com.mshop.app.product.exception.attribute.AttributeNotFoundException;
import com.mshop.app.product.exception.attribute.AttributeValueNotFoundException;
import com.mshop.app.product.mapper.AttributeValueDomainMapper;
import com.mshop.app.product.model.Attribute;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.repository.AttributeRepository;
import com.mshop.app.product.repository.AttributeValueRepository;
import com.mshop.app.product.service.AttributeValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttributeValueServiceImpl implements AttributeValueService {

    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeValueDomainMapper attributeValueDomainMapper;

    @Override
    @Transactional
    public AttributeValue create(AttributeValue attributeValue) {
        String attributeCode = attributeValue.getAttributeCode();
        validateAttribute(attributeCode);

        log.info("Create attribute value:{}", attributeValue);
        return attributeValueRepository.save(attributeValue);
    }

    @Override
    public List<AttributeValue> getAllByAttributeCode(String attributeCode, Pagination pagination) {
        if (!attributeRepository.existsByCode(attributeCode)) {
            throw getAttributeNotFoundExceptionByCode(attributeCode);
        }

        return attributeValueRepository.findAllByAttributeCode(attributeCode, pagination);
    }

    @Override
    public AttributeValue getById(String attributeValueId) {
        return findById(attributeValueId);
    }

    @Override
    @Transactional
    public AttributeValue update(AttributeValue attributeValue) {
        AttributeValue attributeValueDb = findById(attributeValue.getId());
        attributeValueDomainMapper.partialUpdate(attributeValue, attributeValueDb);

        log.info("Update attribute value:{}", attributeValueDb);
        return attributeValueRepository.save(attributeValueDb);
    }

    @Override
    @Transactional
    public void disable(String attributeValueId) {
        AttributeValue attributeValue = findById(attributeValueId);

        if (attributeValue.isDisabled()) {
            log.warn("Attribute value [id={}] has been disabled", attributeValueId);
            return;
        }

        attributeValue.disable();
        log.info("Disable attribute value[id={}]", attributeValueId);
        attributeValueRepository.save(attributeValue);
    }

    @Override
    @Transactional
    public void enable(String attributeValueId) {
        AttributeValue attributeValue = findById(attributeValueId);

        if (attributeValue.isEnabled()) {
            log.warn("Attribute value [id={}] has been enabled", attributeValueId);
            return;
        }

        attributeValue.enable();
        log.info("Enable attribute value[id={}]", attributeValueId);
        attributeValueRepository.save(attributeValue);
    }

    private AttributeNotFoundException getAttributeNotFoundExceptionByCode(String attributeCode) {
        log.warn("Attribute [code={}] not found", attributeCode);
        return new AttributeNotFoundException(ProductServiceCode.ATTRIBUTE_NOT_FOUND);
    }

    private void validateAttribute(String attributeCode) {
        Attribute attribute = attributeRepository.findByCode(attributeCode)
                .orElseThrow(() -> getAttributeNotFoundExceptionByCode(attributeCode));

        if (attribute.getDeleted() != null) {
            log.warn("Attribute [code={}] has been disabled", attributeCode);
            throw new AttributeAlreadyDisableException(ProductServiceCode.ATTRIBUTE_ALREADY_DISABLED);
        }
    }

    private AttributeValue findById(String attributeValueId) {
        return attributeValueRepository.findById(attributeValueId)
                .orElseThrow(() -> {
                    log.warn("Attribute value[id={}] not found", attributeValueId);
                    return new AttributeValueNotFoundException(ProductServiceCode.ATTRIBUTE_VALUE_NOT_FOUND);
                });
    }
}
