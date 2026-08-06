package com.mshop.app.product.search;

import com.mshop.app.category.constant.CategoryField;
import com.mshop.app.common.core.searching.SearchConfig;
import com.mshop.app.common.core.searching.filter.FilterField;
import com.mshop.app.common.core.searching.filter.Operators;
import com.mshop.app.common.core.searching.sort.SortField;
import com.mshop.app.product.constant.ProductField;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("productSearchConfig")
public class ProductSearchConfig implements SearchConfig {

    @Override
    public List<FilterField> getSearchableFields() {
        return List.of(
                new FilterField(ProductField.NAME.getField(), String.class, Collections.singletonList(Operators.LIKE)),
                new FilterField(CategoryField.PATH.getField(), String.class, Collections.singletonList(Operators.EQUAL))
        );
    }

    @Override
    public List<SortField> getSortableFields() {
        return List.of(
                new SortField(ProductField.CREATED_AT.getField())
        );
    }
}
