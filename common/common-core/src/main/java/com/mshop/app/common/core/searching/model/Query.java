package com.mshop.app.common.core.searching.model;

import com.mshop.app.common.core.searching.filter.FilterCondition;
import com.mshop.app.common.core.searching.sort.SortDirection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class Query {
    private Pagination pagination;
    private Map<String, SortDirection> sortBy;
    private List<FilterCondition> filters;
}