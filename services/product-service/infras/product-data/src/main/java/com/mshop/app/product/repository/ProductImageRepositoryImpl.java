package com.mshop.app.product.repository;

import com.mshop.app.common.core.exception.ConflictException;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.jpa.entity.ProductImageEntity;
import com.mshop.app.product.jpa.repo.ProductImageJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductImageRepositoryImpl implements ProductImageRepository {

    private final ProductImageJPARepository productImageJPARepository;

    @Override
    @Transactional
    public void saveAll(String productId, List<String> fileNames) {
        List<ProductImageEntity> entities = fileNames.stream()
                .map(fileName -> ProductImageEntity.builder()
                        .productId(productId)
                        .fileName(fileName).build())
                .collect(Collectors.toUnmodifiableList());
        try {
            productImageJPARepository.saveAll(entities);
        } catch (DataIntegrityViolationException e) {
            log.error("Image already exist in this product.", e);
            throw new ConflictException(ProductServiceCode.PRODUCT_IMAGE_ALREADY_EXIST);
        }
    }

    @Override
    public Set<String> findAllFileNamesByProductIdAndFileNameIn(String productId, Set<String> fileNames) {
        return productImageJPARepository.findAllFileNamesByProductIdAndFileNameIn(productId, fileNames);
    }

    @Override
    @Transactional
    public void removeAllByProductIdAndFileNameIn(String productId, Set<String> fileNames) {
        int affectedRows = productImageJPARepository.removeAllByProductIdAndFileNameIn(productId, fileNames);
        log.info("Removed {} rows in product image", affectedRows);
    }

    @Override
    public List<String> findAllFileNamesByProductId(String productId) {
        return productImageJPARepository.findAllFileNamesByProductId(productId);
    }
}
