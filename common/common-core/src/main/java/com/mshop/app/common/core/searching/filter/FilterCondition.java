package com.mshop.app.common.core.searching.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class FilterCondition {
    private String field;
    private Operators operator;
    private Object value;
}