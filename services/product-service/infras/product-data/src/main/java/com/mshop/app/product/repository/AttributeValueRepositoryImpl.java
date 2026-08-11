package com.mshop.app.product.repository;

import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.attribute.AttributeValueAlreadyExistException;
import com.mshop.app.product.jpa.entity.AttributeValueEntity;
import com.mshop.app.product.jpa.repo.AttributeValueJPARepository;
import com.mshop.app.product.mapper.AttributeValueMapper;
import com.mshop.app.product.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AttributeValueRepositoryImpl implements AttributeValueRepository {

    private final AttributeValueMapper attributeValueMapper;
    private final AttributeValueJPARepository attributeValueJPARepository;

    @Override
    @Transactional
    public AttributeValue save(AttributeValue attributeValue) {
        try {
            AttributeValueEntity createdValue = attributeValueJPARepository.saveAndFlush(
                    attributeValueMapper.toEntity(attributeValue)
            );

            return attributeValueMapper.toDto(createdValue);
        } catch (DataIntegrityViolationException e) {
            log.error("Attribute value {} already exists!", attributeValue, e);
            throw new AttributeValueAlreadyExistException(ProductServiceCode.ATTRIBUTE_VALUE_ALREADY_EXIST);
        }
    }
}
