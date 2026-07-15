package com.mshop.app.common.core.jpa.spec;

import com.mshop.app.common.core.searching.filter.FilterCondition;
import com.mshop.app.common.core.utils.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SpecificationBuilder {

    private SpecificationBuilder() {
    }

    public static <T> Specification<T> buildSpecification(FilterCondition condition) {
        return (root, query, cb) -> toPredicate(cb, root, condition);
    }


    private static Predicate toPredicate(CriteriaBuilder cb, Root<?> root, FilterCondition condition) {
        if (condition == null)
            return cb.conjunction();

        String field = StringUtils.kebabCaseToCamelCase(condition.getField());
        return switch (condition.getOperator()) {
            case LIKE -> cb.like(root.get(field), condition.getValue() + "%");
            case EQUAL -> cb.equal(root.get(field), condition.getValue());
            case GE -> greaterThanOrEqualTo(cb, root.get(field), condition);
            case LE -> lessThanOrEqualTo(cb, root.get(field), condition);
            case IS_NULL -> cb.isNull(root.get(field));
        };
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Predicate greaterThanOrEqualTo(CriteriaBuilder cb, Expression expression, FilterCondition condition) {
        if (condition.getValue() == null)
            return cb.conjunction();

        if (condition.getValue() instanceof LocalDate value) {
            return cb.greaterThanOrEqualTo(expression, value);
        }

        if (condition.getValue() instanceof LocalDateTime value) {
            return cb.greaterThanOrEqualTo(expression, value);
        }

        if (condition.getValue() instanceof Long value) {
            return cb.greaterThanOrEqualTo(expression, value);
        }
        return cb.greaterThanOrEqualTo(expression, condition.getValue().toString());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Predicate lessThanOrEqualTo(CriteriaBuilder cb, Expression expression, FilterCondition condition) {
        if (condition.getValue() == null)
            return cb.conjunction();

        if (condition.getValue() instanceof LocalDate value) {
            return cb.lessThan(expression, value);
        }

        if (condition.getValue() instanceof LocalDateTime value) {
            return cb.lessThan(expression, value);
        }

        if (condition.getValue() instanceof Long value) {
            return cb.lessThan(expression, value);
        }
        return cb.lessThan(expression, condition.getValue().toString());
    }
}
