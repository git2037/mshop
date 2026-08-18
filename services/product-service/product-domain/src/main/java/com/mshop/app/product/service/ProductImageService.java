package com.mshop.app.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ProductImageService {
    void create(String productId, List<MultipartFile> files);

    void remove(String productId, Set<String> fileNames);
}
