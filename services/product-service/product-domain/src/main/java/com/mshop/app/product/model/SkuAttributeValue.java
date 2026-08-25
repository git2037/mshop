package com.mshop.app.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkuAttributeValue {
    private String skuId;
    private String skuCode;
    private String attributeCode;
    private String attributeName;
    private String attributeValue;
    private int stock;
    private Float price;
}
