package com.mshop.app.product.jpa.entity;

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
@Table(name = "product_attribute_value", schema = "product_service")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductAttributeValueEntity extends BaseEntity {
    @Column(name = "attribute_value_id")
    private String attributeValueId;

    @Column(name = "product_id")
    private String productId;
}