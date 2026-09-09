package com.mshop.app.product.service.impl;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.constant.ProductServiceConstant;
import com.mshop.app.product.exception.ProductServiceCode;
import com.mshop.app.product.exception.category.CategoryNotFoundException;
import com.mshop.app.product.exception.category.CategoryNotMoveException;
import com.mshop.app.product.mapper.CategoryDomainMapper;
import com.mshop.app.product.model.Category;
import com.mshop.app.product.repository.CategoryRepository;
import com.mshop.app.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDomainMapper categoryMapper;

    @Override
    @Transactional
    public Category create(Category category) {
        String parentId = category.getParentId();
        String code = category.getCode();

        boolean hasParent = StringUtils.hasText(parentId);
        String path = hasParent
                ? resolveChildPath(parentId, code)
                : ProductServiceConstant.FORWARD_SLASH + code;

        category.setParentId(hasParent ? parentId : null);
        category.setPath(path);

        log.info("Create category: {}", category);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll(Query query) {
        return categoryRepository.findAll(query);
    }

    @Override
    public List<Category> getAllRootCategories() {
        return categoryRepository.findAllByParentIdIsNullAndDeletedIsNull();
    }

    @Override
    public List<Category> getAllChildrenCategories(String parentId) {
        Category category = findById(parentId);
        return categoryRepository.findAllByPathStartsWithAndDeletedIsNull(category.getPath() + ProductServiceConstant.FORWARD_SLASH);
    }

    @Override
    public List<Category> getPathToRoot(String childId) {
        Category childCategory = findEnableCategoryById(childId);
        List<String> paths = extractChainPaths(childCategory.getPath());

        List<Category> categories = categoryRepository.findAllByPathIn(paths);

        return categories.stream()
                .sorted(
                        Comparator.comparingInt(c -> c.getPath().length())
                )
                .toList();
    }

    @Override
    public Category getById(String id) {
        return findById(id);
    }

    @Override
    public Category getEnableCategoryById(String id) {
        return findEnableCategoryById(id);
    }

    @Override
    @Transactional
    public Category update(String categoryId, Category payload) {
        Category category = findById(categoryId);
        categoryMapper.update(payload, category);

        log.info("Update category[id={}]: {}", categoryId, category);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void move(String categoryId, String newParentId) {
        Category category = findById(categoryId);
        Category parentCategory = Category.builder().path("")
                .id(null).build();
        if (newParentId != null) {
            parentCategory = findById(newParentId);
        }

        if (parentCategory.getPath().startsWith(category.getPath())) {
            log.warn("Can not move category[id={}] to its own subtree", categoryId);
            throw new CategoryNotMoveException(ProductServiceCode.CATEGORY_CAN_NOT_MOVE);
        }

        log.info("Move category[id={}, path={}, parentId={}] to parentId={}", categoryId, category.getPath(),
                category.getParentId(), newParentId);
        category.setParentId(parentCategory.getId());
        categoryRepository.save(category);

        String oldPath = category.getPath();
        String newPath = parentCategory.getPath() + ProductServiceConstant.FORWARD_SLASH + category.getCode();
        log.info("Update paths for category[id={}] subtree", category.getId());
        categoryRepository.updatePathBatch(oldPath, newPath);
    }

    @Override
    @Transactional
    public void disable(String categoryId) {
        Category category = findById(categoryId);
        if (category.isDisabled()) {
            log.warn("Category[id={}] is already disabled", categoryId);
        } else {
            log.info("Disable category[id={}] from DB", categoryId);
            categoryRepository.disableBatchByPath(category.getPath());
        }

    }

    @Override
    @Transactional
    public void enable(String categoryId) {
        Category category = findById(categoryId);
        if (category.isEnabled()) {
            log.warn("Category[id={}] is already enable", categoryId);
        } else {
            log.info("Enable category[id={}] from DB", categoryId);
            categoryRepository.enableBatchByPath(category.getPath());
        }
    }

    private String resolveChildPath(String parentId, String code) {
        Category parentCategory = findById(parentId);
        return parentCategory.getPath() + ProductServiceConstant.FORWARD_SLASH + code;
    }

    private List<String> extractChainPaths(String path) {
        if (path == null || path.isBlank()) return List.of();

        String[] parts = path.split(ProductServiceConstant.FORWARD_SLASH);
        List<String> result = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (String part : parts) {
            if (part.isEmpty()) continue;

            stringBuilder.append(ProductServiceConstant.FORWARD_SLASH).append(part);
            result.add(stringBuilder.toString());
        }

        return result;
    }

    public Category findById(String id) {
        return findCategory(id,
                () -> categoryRepository.findById(id));
    }

    public Category findEnableCategoryById(String id) {
        return findCategory(id,
                () -> categoryRepository.findByIdAndDeletedIsNull(id));
    }

    private Category findCategory(String categoryId, Supplier<Optional<Category>> supplier) {
        return supplier.get().orElseThrow(
                () -> {
                    log.warn("Category [id={}] not found", categoryId);
                    return new CategoryNotFoundException(ProductServiceCode.CATEGORY_NOT_FOUND);
                });
    }
}
