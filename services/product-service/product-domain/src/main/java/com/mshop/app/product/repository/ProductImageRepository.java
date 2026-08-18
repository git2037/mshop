package com.mshop.app.product.repository;

import java.util.List;
import java.util.Set;

public interface ProductImageRepository {
    void saveAll(String productId, List<String> fileNames);

    Set<String> findAllFileNamesByProductIdAndFileNameIn(String productId, Set<String> fileNames);

    void removeAllByProductIdAndFileNameIn(String productId, Set<String> fileNames);
}