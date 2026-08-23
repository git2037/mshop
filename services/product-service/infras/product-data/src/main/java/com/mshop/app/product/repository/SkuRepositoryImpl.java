package com.mshop.app.product.repository;

import com.mshop.app.common.core.exception.ConflictException;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.jpa.entity.SkuEntity;
import com.mshop.app.product.jpa.repo.SkuJPARepository;
import com.mshop.app.product.mapper.SkuMapper;
import com.mshop.app.product.model.Sku;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SkuRepositoryImpl implements SkuRepository {

    private final SkuMapper skuMapper;
    private final SkuJPARepository skuJPARepository;

    @Override
    @Transactional
    public Sku save(Sku sku) {
        try {
            SkuEntity createdSku = skuJPARepository.saveAndFlush(skuMapper.toEntity(sku));
            return skuMapper.toDto(createdSku);
        } catch (DataIntegrityViolationException exception) {
            log.error("Sku already exists", exception);
            throw new ConflictException(ProductServiceCode.SKU_ALREADY_EXIST);
        }
    }
}
