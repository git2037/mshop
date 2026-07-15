package com.mshop.app.common.core.searching;

import com.mshop.app.common.core.searching.sort.SortField;
import com.mshop.app.common.core.searching.filter.FilterField;

import java.util.List;

public interface SearchConfig {
    List<FilterField> getSearchableFields();
    List<SortField> getSortableFields();
}
