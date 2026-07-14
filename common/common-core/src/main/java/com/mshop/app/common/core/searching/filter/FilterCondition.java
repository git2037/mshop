package com.mshop.app.common.core.searching.filter;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FilterCondition {
    private String field;
    private Operators operator;
    private Object value;
}