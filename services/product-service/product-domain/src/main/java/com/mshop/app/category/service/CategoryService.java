package com.mshop.app.category.service;

import com.mshop.app.category.model.Category;
import com.mshop.app.common.core.searching.model.Query;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    List<Category> getAll(Query query);

    List<Category> getAllRootCategories();

    List<Category> getAllChildrenCategories(String parentId);

    List<Category> getPathToRoot(String childId);

    Category getById(String id);

    Category getEnableCategoryById(String id);

    Category update(String categoryId, Category payload);

    void move(String categoryId, String newParentId);

    void disable(String categoryId);

    void enable(String categoryId);
}
