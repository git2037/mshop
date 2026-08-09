package com.mshop.app.attribute.repository;

import com.mshop.app.ProductServiceCode;
import com.mshop.app.attribute.exception.AttributeAlreadyExistException;
import com.mshop.app.attribute.jpa.entity.AttributeEntity;
import com.mshop.app.attribute.jpa.repo.AttributeJPARepository;
import com.mshop.app.attribute.mapper.AttributeMapper;
import com.mshop.app.attribute.model.Attribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AttributeRepositoryImpl implements AttributeRepository {

    private final AttributeMapper attributeMapper;
    private final AttributeJPARepository  attributeJPARepository;

    @Override
    public Attribute save(Attribute attribute) {
        try {
            AttributeEntity entity = attributeJPARepository.saveAndFlush(
                    attributeMapper.toEntity(attribute)
            );

            return attributeMapper.toDto(entity);
        } catch (DataIntegrityViolationException e) {
            log.error("Attribute already exists!", e);
            throw new AttributeAlreadyExistException(ProductServiceCode.ATTRIBUTE_ALREADY_EXIST);
        }
    }
}
