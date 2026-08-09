package com.mshop.app.attribute.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttributeField {
    ID("id"),
    NAME("name"),
    CODE("code"),
    CREATED_AT("created-at"),
    DELETED("deleted"),
    ;

    private final String field;
}
