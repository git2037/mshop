package com.mshop.app.common.core.searching.parser;

import com.mshop.app.common.core.searching.filter.FilterCondition;
import com.mshop.app.common.core.searching.filter.FilterField;
import com.mshop.app.common.core.searching.filter.FilterMapper;
import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.sort.SortDirection;
import com.mshop.app.common.core.searching.sort.SortField;
import com.mshop.app.common.core.searching.sort.SortMapper;
import com.mshop.app.common.core.searching.validator.QueryParamValidator;

import java.util.List;
import java.util.Map;

public class QueryParamParser {
    private QueryParamParser() {
        /* This utility class should not be instantiated */
    }

    public static Query parseQueryParam(Map<String, String> queryParams,
                                        List<String> sort,
                                        List<FilterField> allowedSearchField,
                                        List<SortField> allowedSortField
    ) {
        Map<String, String> params = QueryParamValidator.validateParams(queryParams);
        Pagination pagination = PaginationPaser.parse(params);

        params = QueryParamValidator.removePaginationParams(params);

        Map<String, SortDirection> sortBy = SortMapper.map(sort, allowedSortField);
        List<FilterCondition> filtersField = FilterMapper.map(params, allowedSearchField);

        return Query.builder()
                .sortBy(sortBy)
                .pagination(pagination)
                .filters(filtersField).build();
    }
}
