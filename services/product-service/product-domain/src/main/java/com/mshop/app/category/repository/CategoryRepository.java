package com.mshop.app.category.repository;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.category.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository {

    Optional<Category> findById(String id);

    Category save(Category category);

    List<Category> findAll(Query query);

    List<Category> findAllByPathIn(List<String> paths);

    void updatePathBatch(String oldPath, String newPath);

    void disableBatchByPath(String path);

    void enableBatchByPath(String path);

    List<Category> findAllByParentIdIsNullAndDeletedIsNull();

    Optional<Category> findByIdAndDeletedIsNull(String id);

    List<Category> findAllByPathStartsWithAndDeletedIsNull(String path);

    Set<String> findLeafNodes(Set<String> categoryIds);
}