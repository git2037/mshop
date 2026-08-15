package com.mshop.app.product.repository;

import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.attribute.AttributeValueAlreadyExistException;
import com.mshop.app.product.jpa.entity.AttributeValueEntity;
import com.mshop.app.product.jpa.repo.AttributeValueJPARepository;
import com.mshop.app.product.mapper.AttributeValueMapper;
import com.mshop.app.product.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public List<AttributeValue> findAllByAttributeCode(String attributeCode, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        return attributeValueJPARepository.findAllByAttributeCode(attributeCode, pageable).stream()
                .map(attributeValueMapper::toDto)
                .toList();
    }

    @Override
    public Optional<AttributeValue> findById(String attributeValueId) {
        return attributeValueJPARepository.findById(attributeValueId)
                .map(attributeValueMapper::toDto);
    }

    @Override
    @Transactional
    public void disableAllByAttributeCode(String attributeCode) {
        int affectedRows = attributeValueJPARepository.disableAllByAttributeCode(attributeCode);
        log.info("Disabled {} row(s).", affectedRows);
    }

    @Override
    @Transactional
    public void enableAllByAttributeCode(String attributeCode) {
        int affectedRows = attributeValueJPARepository.enableAllByAttributeCode(attributeCode);
        log.info("Enabled {} row(s).", affectedRows);
    }

    @Override
    public Set<AttributeValue> findAllByIdIn(Set<String> attributeValueIds) {
        return attributeValueJPARepository.findAllByIdIn(attributeValueIds).stream()
                .map(attributeValueMapper::toDto).collect(Collectors.toSet());
    }

    @Override
    public List<AttributeValue> findAllInProductAttributeValueByProductId(String productId) {
        return attributeValueJPARepository.findAllInProductAttributeValueByProductId(productId)
                .stream()
                .map(attributeValueMapper::toDto)
                .toList();
    }

    @Override
    public List<AttributeValue> findAllEnableAttributeValueInProductAttributeValueByProductId(String productId) {
        return attributeValueJPARepository
                .findAllEnableAttributeValueInProductAttributeValueByProductId(productId)
                .stream()
                .map(attributeValueMapper::toDto)
                .toList();
    }
}
