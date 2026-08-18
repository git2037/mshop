package com.mshop.app.product.service.impl;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.attribute.AttributeNotFoundException;
import com.mshop.app.product.mapper.AttributeDomainMapper;
import com.mshop.app.product.model.Attribute;
import com.mshop.app.product.repository.AttributeRepository;
import com.mshop.app.product.repository.AttributeValueRepository;
import com.mshop.app.product.service.AttributeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeDomainMapper attributeDomainMapper;
    private final AttributeValueRepository attributeValueRepository;

    @Override
    @Transactional
    public Attribute create(Attribute attribute) {
        log.info("Create attribute:{}", attribute);
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
    @Transactional
    public Attribute update(Attribute attribute) {
        Attribute attributeDb = findById(attribute.getId());

        attributeDomainMapper.partialUpdate(attribute, attributeDb);
        log.info("Update attribute:{}", attribute);
        return attributeRepository.save(attributeDb);
    }

    @Override
    @Transactional
    public void disable(String attributeId) {
        Attribute attributeDb = findById(attributeId);

        if (attributeDb.getDeleted() != null) {
            log.warn("Attribute[id={}] has been disabled", attributeId);
            return;
        }

        attributeDb.disable();
        log.info("Disable attribute[id={}]", attributeId);
        attributeRepository.save(attributeDb);

        String attributeCode = attributeDb.getCode();
        log.info("Disable attribute values[attribute code={}]", attributeCode);
        attributeValueRepository.disableAllByAttributeCode(attributeCode);
    }

    @Override
    @Transactional
    public void enable(String attributeId) {
        Attribute attributeDb = findById(attributeId);

        if (attributeDb.getDeleted() == null) {
            log.warn("Attribute[id={}] has been enabled", attributeId);
            return;
        }

        attributeDb.enable();
        log.info("Enable attribute[id={}]", attributeId);
        attributeRepository.save(attributeDb);

        String attributeCode = attributeDb.getCode();
        log.info("Enable attribute values[attribute code={}]", attributeCode);
        attributeValueRepository.enableAllByAttributeCode(attributeCode);
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
