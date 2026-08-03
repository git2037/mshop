package com.mshop.app.category.jpa.entity;

import com.mshop.app.common.core.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(name = "category", schema = "product_service")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "parent_id", length = 36)
    private String parentId;

    @Column(name = "path")
    private String path;
}