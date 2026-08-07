package com.mshop.app.category.repository;

import com.mshop.app.ProductCode;
import com.mshop.app.category.exception.CategoryAlreadyExistException;
import com.mshop.app.category.jpa.entity.CategoryEntity;
import com.mshop.app.category.jpa.repo.CategoryJPARepository;
import com.mshop.app.category.mapper.CategoryMapper;
import com.mshop.app.category.model.Category;
import com.mshop.app.common.core.jpa.spec.SpecificationBuilder;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.PaginationParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper mapper;
    private final CategoryJPARepository jpaRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> findById(String id) {
        return jpaRepository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public Category save(Category category) {
        try {
            CategoryEntity createdCategory = jpaRepository
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
        Pageable pageable = PaginationParser.parsePageable(query);

        Specification<CategoryEntity> specification = SpecificationBuilder
                .buildSpecification(query.getFilters());

        Page<CategoryEntity> entityPage = jpaRepository.findAll(specification, pageable);

        return entityPage.getContent().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllByPathIn(List<String> paths) {
        return jpaRepository.findAllByPathIn(paths).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void updatePathBatch(String oldPath, String newPath) {
        try {
            jpaRepository.updatePathBatch(oldPath, newPath);
        } catch (DataIntegrityViolationException exception) {
            log.error("Path conflict! Moving category[path={}] to an existing path '{}'", oldPath, newPath, exception);
            throw new CategoryAlreadyExistException(ProductCode.CATEGORY_ALREADY_EXIST);
        }
    }

    @Override
    @Transactional
    public void disableBatchByPath(String path) {
        jpaRepository.disableBatchByPath(path);
    }

    @Override
    @Transactional
    public void enableBatchByPath(String path) {
        jpaRepository.enableBatchByPath(path);
    }

    @Override
    public List<Category> findAllByParentIdIsNullAndDeletedIsNull() {
        return jpaRepository.findAllByParentIdIsNullAndDeletedIsNull().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Optional<Category> findByIdAndDeletedIsNull(String id) {
        return jpaRepository.findByIdAndDeletedIsNull(id).map(mapper::toDto);
    }

    @Override
    public List<Category> findAllByPathStartsWithAndDeletedIsNull(String path) {
        return jpaRepository.findAllByDeletedIsNullAndPathStartsWith(path).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public Set<String> findLeafNodes(Set<String> categoryIds) {
        return jpaRepository.findLeafNodes(categoryIds);
    }
}
