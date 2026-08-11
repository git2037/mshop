package com.mshop.app.product.service.impl;

import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.attribute.AttributeAlreadyDisableException;
import com.mshop.app.product.exception.attribute.AttributeNotFoundException;
import com.mshop.app.product.model.Attribute;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.repository.AttributeRepository;
import com.mshop.app.product.repository.AttributeValueRepository;
import com.mshop.app.product.service.AttributeValueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttributeValueServiceImpl implements AttributeValueService {

    private final AttributeValueRepository attributeValueRepository;
    private final AttributeRepository attributeRepository;

    @Override
    public AttributeValue create(AttributeValue attributeValue) {
        String attributeCode = attributeValue.getAttributeCode().toUpperCase();
        validateAttribute(attributeCode);

        log.info("Create attribute value:{}", attributeValue);
        return attributeValueRepository.save(attributeValue);
    }

    private void validateAttribute(String attributeCode) {
        Attribute attribute = attributeRepository.findByCode(attributeCode)
                .orElseThrow(() -> {
                    log.warn("Attribute [code={} not found]", attributeCode);
                    return new AttributeNotFoundException(ProductServiceCode.ATTRIBUTE_NOT_FOUND);
                });

        if (attribute.getDeleted() != null) {
            log.warn("Attribute [code={}] has been disabled", attributeCode);
            throw new AttributeAlreadyDisableException(ProductServiceCode.ATTRIBUTE_ALREADY_DISABLED);
        }
    }
}
