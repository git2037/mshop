package com.mshop.app.product.repository;

import com.mshop.app.ProductCode;
import com.mshop.app.product.exception.ProductAlreadyExistException;
import com.mshop.app.product.jpa.repo.ProductJPARepository;
import com.mshop.app.product.mapper.ProductMapper;
import com.mshop.app.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMapper mapper;
    private final ProductJPARepository jpaRepository;

    @Override
    @Transactional
    public Product create(Product product) {
        try {
            return mapper.toDto(jpaRepository.saveAndFlush(mapper.toEntity(product)));
        } catch (DataIntegrityViolationException exception) {
            log.info("Product already exists");
            throw new ProductAlreadyExistException(ProductCode.PRODUCT_ALREADY_EXIST);
        }
    }
}
