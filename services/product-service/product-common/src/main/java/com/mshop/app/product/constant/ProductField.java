package com.mshop.app.product.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductField {
    ID("id"),
    NAME("name"),
    CREATED_AT("created-at"),
    DELETED("deleted"),
    ;

    private final String field;
}
