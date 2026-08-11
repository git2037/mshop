package com.mshop.app.product.repository;

import com.mshop.app.common.core.jpa.spec.SpecificationBuilder;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.PaginationParser;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.attribute.AttributeAlreadyExistException;
import com.mshop.app.product.jpa.entity.AttributeEntity;
import com.mshop.app.product.jpa.repo.AttributeJPARepository;
import com.mshop.app.product.mapper.AttributeMapper;
import com.mshop.app.product.model.Attribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AttributeRepositoryImpl implements AttributeRepository {

    private final AttributeMapper attributeMapper;
    private final AttributeJPARepository  attributeJPARepository;

    @Override
    @Transactional
    public Attribute save(Attribute attribute) {
        try {
            AttributeEntity entity = attributeJPARepository.saveAndFlush(
                    attributeMapper.toEntity(attribute)
            );

            return attributeMapper.toDto(entity);
        } catch (DataIntegrityViolationException e) {
            log.error("Attribute[name={}, code={}] already exists!", attribute.getName(), attribute.getCode(), e);
            throw new AttributeAlreadyExistException(ProductServiceCode.ATTRIBUTE_ALREADY_EXIST);
        }
    }

    @Override
    public List<Attribute> findAll(Query query) {
        Pageable pageable = PaginationParser.parsePageable(query);

        Specification<AttributeEntity> specification = SpecificationBuilder
                .buildSpecification(query.getFilters());

        Page<AttributeEntity> entityPage = attributeJPARepository.findAll(specification, pageable);

        return entityPage.getContent().stream()
                .map(attributeMapper::toDto)
                .toList();
    }

    @Override
    public Optional<Attribute> findById(String attributeId) {
        return attributeJPARepository.findById(attributeId)
                .map(attributeMapper::toDto);
    }

    @Override
    public Optional<Attribute> findByCode(String attributeCode) {
        return attributeJPARepository.findByCode(attributeCode).map(attributeMapper::toDto);
    }

    @Override
    public boolean existsByCode(String attributeCode) {
        return attributeJPARepository.existsByCode(attributeCode);
    }
}
