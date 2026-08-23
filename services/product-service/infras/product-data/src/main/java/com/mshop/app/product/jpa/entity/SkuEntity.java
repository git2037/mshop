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
@Table(name = "sku", schema = "product_service")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SkuEntity extends BaseEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "product_id")
    private String productId;
}