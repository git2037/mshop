package com.mshop.app.common.core.searching.filter;

import java.util.List;

public record FilterField(String field, Class<?> type, List<Operators> operators) {
}
