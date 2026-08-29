package com.mshop.app.product.service.impl;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.mapper.ProductDomainMapper;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.reader.ProductReader;
import com.mshop.app.product.repository.FileStorageRepository;
import com.mshop.app.product.repository.ProductRepository;
import com.mshop.app.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDomainMapper productDomainMapper;
    private final ProductReader productReader;
    private final FileStorageRepository fileStorageRepository;

    @Override
    @Transactional
    public Product create(Product product) {
        log.info("Create product: {}", product);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll(Query query) {
        return productRepository.findAll(query).stream()
                .map(this::setThumbnailUrl)
                .toList();
    }

    @Override
    public List<Product> getAllEnableProduct(Query query) {
        return productRepository.findAllEnableProduct(query).stream()
                .map(this::setThumbnailUrl)
                .toList();
    }

    @Override
    public Product getById(String id) {
        return setThumbnailUrl(productReader.findById(id));
    }

    @Override
    public Product getEnableProductById(String id) {
        return setThumbnailUrl(productReader.findEnableProductById(id));
    }

    @Override
    @Transactional
    public Product update(Product product) {
        String productId = product.getId();
        Product productDB = productReader.findById(productId);
        productDomainMapper.updateProductFromDto(product, productDB);

        log.info("Update product[id={}]: {}", productId, productDB);
        return productRepository.save(productDB);
    }

    @Override
    @Transactional
    public void disable(String productId) {
        Product product = productReader.findById(productId);

        if (product.getDeleted() != null) {
            log.info("Product[id={}] already disabled", product.getId());
            return;
        }

        product.disable();
        log.info("Disable product[id={}]", productId);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void enable(String productId) {
        Product product = productReader.findById(productId);

        if (product.getDeleted() == null) {
            log.info("Product[id={}] already enabled", product.getId());
            return;
        }

        product.enable();
        log.info("Enable product[id={}]", productId);
        productRepository.save(product);
    }

    private Product setThumbnailUrl(Product product) {
        String thumbnail = product.getThumbnail();

        if (thumbnail != null) {
            product.setThumbnail(
                    fileStorageRepository.buildUrlImages(thumbnail)
            );
        }

        return product;
    }
}
