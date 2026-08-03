package com.mshop.app.category.service.impl;

import com.mshop.app.category.exception.CategoryCanNotMove;
import com.mshop.app.category.exception.CategoryErrorCode;
import com.mshop.app.category.exception.CategoryNotFoundException;
import com.mshop.app.category.model.Category;
import com.mshop.app.category.repository.CategoryRepository;
import com.mshop.app.category.service.CategoryService;
import com.mshop.app.common.core.searching.model.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private static final String FORWARD_SLASH = "/";

    @Override
    public Category create(Category category) {
        String parentId = category.getParentId();
        String code = category.getCode();

        boolean hasParent = StringUtils.hasText(parentId);
        String path = hasParent
                ? resolveChildPath(parentId, code)
                : FORWARD_SLASH + code;

        category.setParentId(hasParent ? parentId : null);
        category.setPath(path);

        log.info("Save category[name={}, code={}, parentId={}, path={}] to DB", category.getName(), code, parentId, path);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAll(Query query) {
        log.info("Get all categories from DB");
        return categoryRepository.findAll(query);
    }

    @Override
    public List<Category> getAllRootCategories() {
        log.info("Get all root categories from DB");
        return categoryRepository.findAllByParentIdIsNullAndDeletedIsNull();
    }

    @Override
    public List<Category> getAllChildrenCategories(String parentId) {
        Category category = findByIdAndDeletedIsNull(parentId);

        log.info("Get all children categories of parentId={} from DB", parentId);
        return categoryRepository.findAllByPathStartsWithAndDeletedIsNull(category.getPath() + FORWARD_SLASH);
    }

    @Override
    public List<Category> getPathToRoot(String childId) {
        Category category = findByIdAndDeletedIsNull(childId);
        List<String> paths = extractChainPaths(category.getPath());

        log.info("Get category chain from childId={} to root category from DB", childId);
        List<Category> categories = categoryRepository.findAllByPathIn(paths);

        return categories.stream()
                .sorted(
                        Comparator.comparingInt(c -> c.getPath().length())
                )
                .toList();
    }

    @Override
    public Category getCategoryById(String id) {
        log.info("Get category by id={}", id);
        return findByIdAndDeletedIsNull(id);
    }

    @Override
    public Category update(String categoryId, Category payload) {
        Category category = findById(categoryId);
        String name = category.getName();
        String payloadName = payload.getName();
        if (isUpdate(name, payloadName)) {
            category.setName(payloadName);
        }

        String path = category.getPath();
        String payloadCode = payload.getCode();
        if (isUpdate(path, payloadCode)) {
            category.setPath(createNewPath(path, payloadCode));
            category.setCode(payloadCode);
        }

        log.info("Update category[id={}, name={}, code={}, path={}] to DB", category.getId(),
                category.getName(), category.getCode(), category.getPath());
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
            throw new CategoryCanNotMove(CategoryErrorCode.CATEGORY_CAN_NOT_MOVE);
        }

        log.info("Move category[id={}, path={}, parentId={}] to parentId={}", categoryId, category.getPath(),
                category.getParentId(), newParentId);
        category.setParentId(parentCategory.getId());
        categoryRepository.save(category);

        String oldPath = category.getPath();
        String newPath = parentCategory.getPath() + FORWARD_SLASH + category.getCode();
        log.info("Update paths for category[id={}] subtree to DB", category.getId());
        categoryRepository.updatePathBatch(oldPath, newPath);
    }

    @Override
    public void disable(String categoryId) {
        Category category = findById(categoryId);
        if (category.getDeleted() != null) {
            log.warn("Category[id={}] is already disabled", categoryId);
        } else {
            log.info("Disable category[id={}] from DB", categoryId);
            categoryRepository.disableBatchByPath(category.getPath());
        }

    }

    @Override
    public void enable(String categoryId) {
        Category category = findById(categoryId);
        if (category.getDeleted() == null) {
            log.warn("Category[id={}] is already enable", categoryId);
        } else {
            log.info("Enable category[id={}] from DB", categoryId);
            categoryRepository.enableBatchByPath(category.getPath());
        }
    }

    private String resolveChildPath(String parentId, String code) {
        Category parentCategory = findById(parentId);
        return parentCategory.getPath() + FORWARD_SLASH + code;
    }

    private Category findById(String id) {
        log.info("Find category by id={}", id);
        return getOrThrowNotFoundException(
                categoryRepository.findById(id), id
        );
    }

    private Category findByIdAndDeletedIsNull(String id) {
        log.info("Find category by id={} and deleted is null", id);
        return getOrThrowNotFoundException(
                categoryRepository.findByIdAndDeletedIsNull(id), id
        );
    }

    private Category getOrThrowNotFoundException(Optional<Category> categoryOptional, String id) {
        return categoryOptional.orElseThrow(
                () -> {
                    log.warn("Category [id={}] not found]", id);
                    return new CategoryNotFoundException(CategoryErrorCode.CATEGORY_NOT_FOUND);
                });
    }

    private List<String> extractChainPaths(String path) {
        if (path == null || path.isBlank()) return List.of();

        String[] parts = path.split(FORWARD_SLASH);
        List<String> result = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        for (String part : parts) {
            if (part.isEmpty()) continue;

            stringBuilder.append(FORWARD_SLASH).append(part);
            result.add(stringBuilder.toString());
        }

        return result;
    }

    private boolean isUpdate(String oldValue, String newValue) {
        return (newValue != null) && (!newValue.equals(oldValue));
    }

    private String createNewPath(String oldPath, String newCode) {
        return oldPath.substring(0, oldPath.lastIndexOf(FORWARD_SLASH) + 1) + newCode;
    }
}
