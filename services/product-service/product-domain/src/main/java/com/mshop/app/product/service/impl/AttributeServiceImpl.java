package com.mshop.app.product.service.impl;

import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.attribute.AttributeAlreadyDisableException;
import com.mshop.app.product.exception.attribute.AttributeNotFoundException;
import com.mshop.app.product.exception.attribute.AttributeValueNotFoundException;
import com.mshop.app.product.mapper.AttributeDomainMapper;
import com.mshop.app.product.model.Attribute;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.repository.AttributeRepository;
import com.mshop.app.product.repository.AttributeValueRepository;
import com.mshop.app.product.service.AttributeService;
import com.mshop.app.common.core.searching.model.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeDomainMapper attributeDomainMapper;
    private  final AttributeValueRepository attributeValueRepository;

    @Override
    public Attribute create(Attribute attribute) {
        log.info("Create attribute:{}",attribute);
        return attributeRepository.save(attribute);
    }

    @Override
    public List<Attribute> getAttributes(Query query) {
        return attributeRepository.findAll(query);
    }

    @Override
    public Attribute getAttributeById(String attributeId) {
        return findById(attributeId);
    }

    @Override
    public Attribute update(Attribute attribute) {
        Attribute attributeDb = findById(attribute.getId());

        attributeDomainMapper.partialUpdate(attribute, attributeDb);
        log.info("Update attribute:{}",attribute);
        return attributeRepository.save(attributeDb);
    }

    @Override
    public void disable(String attributeId) {
        Attribute attributeDb = findById(attributeId);

        if (attributeDb.getDeleted() != null) {
            log.warn("Attribute[id={}] has been disabled", attributeId);
            return;
        }
        attributeDb.setDeleted(Instant.now());
        log.info("Disable attribute[id={}]",attributeId);
        attributeRepository.save(attributeDb);
    }

    @Override
    public void enable(String attributeId) {
        Attribute attributeDb = findById(attributeId);

        if (attributeDb.getDeleted() == null) {
            log.warn("Attribute[id={}] has been enabled", attributeId);
            return;
        }
        attributeDb.setDeleted(null);
        log.info("Enable attribute[id={}]",attributeId);
        attributeRepository.save(attributeDb);
    }

    @Override
    public AttributeValue createAttributeValue(AttributeValue attributeValue) {
        String attributeCode = attributeValue.getAttributeCode();
        validateAttribute(attributeCode);

        log.info("Create attribute value:{}", attributeValue);
        return attributeValueRepository.save(attributeValue);
    }

    @Override
    public List<AttributeValue> getAttributeValuesByAttributeCode(String attributeCode, Pagination pagination) {
        if (!attributeRepository.existsByCode(attributeCode)) {
            throw getAttributeNotFoundExceptionByCode(attributeCode);
        }

        return attributeValueRepository.findAllByAttributeCode(attributeCode, pagination);
    }

    @Override
    public AttributeValue getAttributeValueByAttributeValueId(String attributeValueId) {
        return attributeValueRepository.findById(attributeValueId)
                .orElseThrow(() -> {
                    log.warn("Attribute value[id={}] not found", attributeValueId);
                    return new AttributeValueNotFoundException(ProductServiceCode.ATTRIBUTE_VALUE_NOT_FOUND);
                });
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

    private Attribute findById(String attributeId) {
        return attributeRepository.findById(attributeId).orElseThrow(
                () -> {
                    log.warn("Attribute[id={}] not found", attributeId);
                    return new AttributeNotFoundException(ProductServiceCode.ATTRIBUTE_NOT_FOUND);
                }
        );
    }
}
