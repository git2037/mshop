package com.mshop.app.category.jpa.repo;

import com.mshop.app.category.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryJPARepository extends JpaRepository<CategoryEntity, String>, JpaSpecificationExecutor<CategoryEntity> {
    Optional<CategoryEntity> findByIdAndDeletedIsNull(String id);

    List<CategoryEntity> findAllByParentIdIsNullAndDeletedIsNull();

    List<CategoryEntity> findAllByDeletedIsNullAndPathStartsWith(String pathStartsWith);

    List<CategoryEntity> findAllByPathIn(List<String> paths);

    @Modifying
    @Query(value = "update CategoryEntity set path = replace(path, :oldPath, :newPath), updatedAt=current_timestamp() where path like concat(:oldPath, '%')")
    void updatePathBatch(@Param("oldPath") String oldPath, @Param("newPath") String newPath);

    @Modifying
    @Query(value = "update CategoryEntity set deleted = current_timestamp(), updatedAt=current_timestamp() where path like concat(:path, '%')")
    void disableBatchByPath(@Param("path") String path);

    @Modifying
    @Query(value = "update CategoryEntity set deleted = null, updatedAt=current_timestamp() where path like concat(:path, '%')")
    void enableBatchByPath(@Param("path") String path);

    boolean existsByIdAndDeletedIsNull(String categoryId);
}
