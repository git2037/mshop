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
@Table(name = "sku_attribute_value", schema = "product_service")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SkuAttributeValueEntity extends BaseEntity {
    @Column(name = "sku_id")
    private String skuId;

    @Column(name = "attribute_value_id")
    private String attributeValueId;
}