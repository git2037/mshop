package com.mshop.app.common.core.searching.sort;

import com.mshop.app.common.core.utils.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SortBuilder {

    private SortBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static Sort buildSort(Map<String, SortDirection> sortValues) {
        if (sortValues == null || sortValues.isEmpty()) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (Map.Entry<String, SortDirection> entry : sortValues.entrySet()) {
            String field = StringUtils.kebabCaseToCamelCase(entry.getKey());
            String direction = entry.getValue().name();
            orders.add(new Sort.Order(Sort.Direction.fromString(direction), field));
        }

        return Sort.by(orders);
    }
}
