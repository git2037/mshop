package com.mshop.app.category.repository;

import com.mshop.app.category.exception.CategoryAlreadyExistException;
import com.mshop.app.ProductCode;
import com.mshop.app.category.jpa.entity.CategoryEntity;
import com.mshop.app.category.jpa.repo.CategoryJPARepository;
import com.mshop.app.category.mapper.CategoryMapper;
import com.mshop.app.category.model.Category;
import com.mshop.app.common.core.jpa.spec.SpecificationBuilder;
import com.mshop.app.common.core.searching.filter.FilterCondition;
import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.sort.SortBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper mapper;
    private final CategoryJPARepository categoryJPARepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(String id) {
        return categoryJPARepository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public Category save(Category category) {
        try {
            CategoryEntity createdCategory = categoryJPARepository
                    .saveAndFlush(mapper.toEntity(category));
            return mapper.toDto(createdCategory);
        } catch (DataIntegrityViolationException exception) {
            log.warn("Category [name={}, code={}, path={}] already exist",
                    category.getName(), category.getCode(), category.getPath(), exception);
            throw new CategoryAlreadyExistException(ProductCode.CATEGORY_ALREADY_EXIST);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll(Query query) {
        Sort sort = SortBuilder.buildSort(query.getSortBy());
        Pagination pagination = query.getPagination();
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize(), sort);

        Specification<CategoryEntity> specification = Specification.unrestricted();
        for (FilterCondition condition : query.getFilters()) {
            specification = specification.and(SpecificationBuilder.buildSpecification(condition));
        }

        log.debug("Fetching category list with page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<CategoryEntity> entityPage = categoryJPARepository.findAll(specification, pageable);

        return entityPage.getContent().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllByPathIn(List<String> paths) {
        return categoryJPARepository.findAllByPathIn(paths).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void updatePathBatch(String oldPath, String newPath) {
        try {
            categoryJPARepository.updatePathBatch(oldPath, newPath);
        } catch (DataIntegrityViolationException exception) {
            log.error("Path conflict! Moving category[path={}] to an existing path '{}'", oldPath, newPath, exception);
            throw new CategoryAlreadyExistException(ProductCode.CATEGORY_ALREADY_EXIST);
        }
    }

    @Override
    @Transactional
    public void disableBatchByPath(String path) {
        categoryJPARepository.disableBatchByPath(path);
    }

    @Override
    @Transactional
    public void enableBatchByPath(String path) {
        categoryJPARepository.enableBatchByPath(path);
    }

    @Override
    public List<Category> findAllByParentIdIsNullAndDeletedIsNull() {
        return categoryJPARepository.findAllByParentIdIsNullAndDeletedIsNull().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Optional<Category> findByIdAndDeletedIsNull(String id) {
        return categoryJPARepository.findByIdAndDeletedIsNull(id).map(mapper::toDto);
    }

    @Override
    public List<Category> findAllByPathStartsWithAndDeletedIsNull(String path) {
        return categoryJPARepository.findAllByDeletedIsNullAndPathStartsWith(path).stream()
                .map(mapper::toDto)
                .toList();
    }
}
