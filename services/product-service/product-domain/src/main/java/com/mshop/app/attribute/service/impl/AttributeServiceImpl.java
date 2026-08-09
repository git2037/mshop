package com.mshop.app.attribute.service.impl;

import com.mshop.app.ProductServiceCode;
import com.mshop.app.attribute.exception.AttributeNotFoundException;
import com.mshop.app.attribute.model.Attribute;
import com.mshop.app.attribute.repository.AttributeRepository;
import com.mshop.app.attribute.service.AttributeService;
import com.mshop.app.common.core.searching.model.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepository attributeRepository;

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
        return attributeRepository.findById(attributeId).orElseThrow(
                () -> {
                    log.warn("Attribute[id={}] not found", attributeId);
                    return new AttributeNotFoundException(ProductServiceCode.ATTRIBUTE_NOT_FOUND);
                }
        );
    }
}
