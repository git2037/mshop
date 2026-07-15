package com.mshop.app.user.search;

import com.mshop.app.common.core.searching.SearchConfig;
import com.mshop.app.common.core.searching.filter.FilterField;
import com.mshop.app.common.core.searching.filter.Operators;
import com.mshop.app.common.core.searching.sort.SortField;
import com.mshop.app.user.constant.UserField;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class UserSearchConfig implements SearchConfig {

    @Override
    public List<FilterField> getSearchableFields() {
        return List.of(
                new FilterField(UserField.EMAIL.getField(), String.class, Collections.singletonList(Operators.LIKE)),
                new FilterField(UserField.CREATED_AT.getField(), LocalDate.class, List.of(Operators.GE, Operators.LE)),
                new FilterField(UserField.DELETED.getField(), Boolean.class, List.of(Operators.IS_NULL))
        );
    }

    @Override
    public List<SortField> getSortableFields() {
        return List.of(
                new SortField(UserField.CREATED_AT.getField()),
                new SortField(UserField.UPDATED_AT.getField())
        );
    }
}
