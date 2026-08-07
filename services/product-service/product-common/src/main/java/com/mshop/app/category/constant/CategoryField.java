package com.mshop.app.category.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryField {
    ID("id"),
    NAME("name"),
    CODE("code"),
    CREATED_AT("created-at"),
    DELETED("deleted"),
    PATH("path"),
    ;

    private final String field;
}
