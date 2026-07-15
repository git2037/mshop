package com.mshop.app.common.core.searching.sort;

public record SortField(String field, SortDirection direction) {

    public SortField(String field) {
        this(field, SortDirection.ASC);
    }
}
