package com.mshop.app.product.repository;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageRepository {

    String upload(String productId, MultipartFile file);

    void removeAll(List<String> uploadedFileNames);

    String buildUrlImages(String fileName);
}
