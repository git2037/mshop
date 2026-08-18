package com.mshop.app.product.service.impl;

import com.mshop.app.common.core.exception.ResourceNotFoundException;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.reader.ProductReader;
import com.mshop.app.product.repository.FileStorageRepository;
import com.mshop.app.product.repository.ProductImageRepository;
import com.mshop.app.product.service.ProductImageService;
import com.mshop.app.product.validator.ImageValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductReader productReader;
    private final FileStorageRepository fileStorageRepository;
    private final ProductImageRepository productImageRepository;
    private final ImageValidator imageValidator;

    @Override
    public void create(String productId, List<MultipartFile> files) {
        for (MultipartFile file : files) {
            imageValidator.validate(file);
        }

        productReader.existById(productId);

        List<String> uploadedFileNames = new ArrayList<>();

        try {
            log.info("Uploading image");
            files.forEach(file -> {
                String uploadedFileName = fileStorageRepository.upload(productId, file);
                uploadedFileNames.add(uploadedFileName);
            });

            log.info("Saving images to product[id={}]", productId);
            productImageRepository.saveAll(productId, uploadedFileNames);
        } catch (Exception e) {
            log.error("Error for creating product image");
            fileStorageRepository.removeAll(uploadedFileNames);
            throw e;
        }
    }

    @Override
    public void remove(String productId, Set<String> fileNames) {
        Set<String> fileNamesInDB = productImageRepository
                .findAllFileNamesByProductIdAndFileNameIn(productId, fileNames);

        if (fileNamesInDB.size() != fileNames.size()) {
            Set<String> missingFileNames = new HashSet<>(fileNames);
            missingFileNames.removeAll(fileNamesInDB);
            log.warn("Image with names={} not found in product[id={}]", missingFileNames, productId);
            throw new ResourceNotFoundException(ProductServiceCode.PRODUCT_IMAGE_NOT_FOUND,
                    MessageFormat.format("Image with names={0} not found in product", missingFileNames));
        }

        log.info("Removing images from product[id={}]", productId);
        productImageRepository.removeAllByProductIdAndFileNameIn(productId, fileNames);

        log.info("Removing images from storage");
        fileStorageRepository.removeAll(fileNames.stream().toList());
    }
}
