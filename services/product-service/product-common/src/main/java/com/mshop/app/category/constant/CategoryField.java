package com.mshop.app.category.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
public enum CategoryField {
    ID("id"),
    NAME("name"),
    CODE("code"),
    CREATED_AT("created-at"),
    DELETED("deleted");
    ;

    private final String field;
}
