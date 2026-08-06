package com.mshop.app.product.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategoryField {
    ID("id"),
    CATEGORY_ID("category-id"),
    PRODUCT_ID("product-id"),
    ;

    private final String field;
}
