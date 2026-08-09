package com.mshop.app.product.repository;

import com.mshop.app.ProductServiceCode;
import com.mshop.app.category.constant.CategoryField;
import com.mshop.app.common.core.jpa.spec.SpecificationBuilder;
import com.mshop.app.common.core.searching.filter.FilterCondition;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.PaginationParser;
import com.mshop.app.product.exception.ProductAlreadyExistException;
import com.mshop.app.product.jpa.entity.ProductEntity;
import com.mshop.app.product.jpa.repo.ProductJPARepository;
import com.mshop.app.product.jpa.spec.ProductSpecification;
import com.mshop.app.product.mapper.ProductMapper;
import com.mshop.app.product.model.Product;
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
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMapper productMapper;
    private final ProductJPARepository productJPARepository;

    @Override
    @Transactional
    public Product save(Product product) {
        try {
            return productMapper.toDto(productJPARepository.saveAndFlush(productMapper.toEntity(product)));
        } catch (DataIntegrityViolationException exception) {
            log.warn("Product already exists");
            throw new ProductAlreadyExistException(ProductServiceCode.PRODUCT_ALREADY_EXIST);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll(Query query) {
        log.debug("Get all products by query: {}", query);
        Pageable pageable = PaginationParser.parsePageable(query);

        Specification<ProductEntity> specification = getProductSpecification(query.getFilters(), false);

        Page<ProductEntity> entityPage = productJPARepository.findAll(specification, pageable);

        return entityPage.getContent().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllEnableProduct(Query query) {
        Pageable pageable = PaginationParser.parsePageable(query);

        Specification<ProductEntity> specification = getProductSpecification(query.getFilters(), true)
                .and(ProductSpecification.deletedIsNull());

        Page<ProductEntity> entityPage = productJPARepository.findAll(specification, pageable);

        return entityPage.getContent().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(String id) {
        return productJPARepository.findById(id).map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findByIdAndDeletedIsNull(String id) {
        return productJPARepository.findByIdAndDeletedIsNull(id).map(productMapper::toDto);
    }

    @Override
    public boolean existById(String id) {
        return productJPARepository.existsById(id);
    }

    @Override
    @Transactional
    public void disable(String productId) {
        productJPARepository.disable(productId);
    }

    @Override
    @Transactional
    public void enable(String productId) {
        productJPARepository.enable(productId);
    }

    private Specification<ProductEntity> getProductSpecification(List<FilterCondition> conditions, boolean filterEnableCategory) {
        Specification<ProductEntity> specification = Specification.unrestricted();

        for (FilterCondition condition : conditions) {
            if (condition.getField().equals(CategoryField.PATH.getField())) {
                specification = specification.and(
                        ProductSpecification.findByCategoryId(String.valueOf(condition.getValue()), filterEnableCategory)
                );
                continue;
            }
            specification = specification.and(SpecificationBuilder.buildSpecification(condition));
        }
        return specification;
    }
}
