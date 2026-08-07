package com.mshop.app.category.search;

import com.mshop.app.category.constant.CategoryField;
import com.mshop.app.common.core.searching.SearchConfig;
import com.mshop.app.common.core.searching.filter.FilterField;
import com.mshop.app.common.core.searching.filter.Operators;
import com.mshop.app.common.core.searching.sort.SortField;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component("categorySearchConfig")
public class CategorySearchConfig implements SearchConfig {

    @Override
    public List<FilterField> getSearchableFields() {
        return List.of(
                new FilterField(CategoryField.CODE.getField(), String.class, Collections.singletonList(Operators.LIKE)),
                new FilterField(CategoryField.CREATED_AT.getField(), LocalDate.class, List.of(Operators.GE, Operators.LE)),
                new FilterField(CategoryField.DELETED.getField(), Boolean.class, List.of(Operators.IS_NULL))
        );
    }

    @Override
    public List<SortField> getSortableFields() {
        return List.of(
                new SortField(CategoryField.CREATED_AT.getField()),
                new SortField(CategoryField.CODE.getField()),
                new SortField(CategoryField.NAME.getField())
        );
    }
}
