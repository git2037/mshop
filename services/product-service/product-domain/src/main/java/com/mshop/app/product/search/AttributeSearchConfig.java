package com.mshop.app.product.search;

import com.mshop.app.product.constant.AttributeField;
import com.mshop.app.common.core.searching.SearchConfig;
import com.mshop.app.common.core.searching.filter.FilterField;
import com.mshop.app.common.core.searching.filter.Operators;
import com.mshop.app.common.core.searching.sort.SortField;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component("attributeSearchConfig")
public class AttributeSearchConfig implements SearchConfig {

    @Override
    public List<FilterField> getSearchableFields() {
        return List.of(
                new FilterField(AttributeField.CODE.getField(), String.class, Collections.singletonList(Operators.LIKE)),
                new FilterField(AttributeField.NAME.getField(), String.class, Collections.singletonList(Operators.LIKE)),
                new FilterField(AttributeField.CREATED_AT.getField(), LocalDate.class, List.of(Operators.GE, Operators.LE)),
                new FilterField(AttributeField.DELETED.getField(), Boolean.class, List.of(Operators.IS_NULL))
        );
    }

    @Override
    public List<SortField> getSortableFields() {
        return List.of(
                new SortField(AttributeField.CREATED_AT.getField()),
                new SortField(AttributeField.CODE.getField()),
                new SortField(AttributeField.NAME.getField())
        );
    }
}
