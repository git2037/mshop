package com.mshop.app.product.jpa.spec;

import com.mshop.app.product.constant.CategoryField;
import com.mshop.app.product.jpa.entity.CategoryEntity;
import com.mshop.app.common.core.utils.StringUtils;
import com.mshop.app.product.constant.ProductCategoryField;
import com.mshop.app.product.constant.ProductField;
import com.mshop.app.product.jpa.entity.ProductCategoryEntity;
import com.mshop.app.product.jpa.entity.ProductEntity;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    private ProductSpecification() {
    }

    public static Specification<ProductEntity> deletedIsNull() {
        return (root, query, builder) ->
                builder.isNull(root.get(ProductField.DELETED.getField()));
    }

    public static Specification<ProductEntity> findByCategoryId(String categoryPath, boolean filterEnableCategory) {
        return (root, query, builder) -> {
            if (query == null)
                return builder.conjunction();

            Subquery<String> subquery = query.subquery(String.class);
            Root<ProductCategoryEntity> productCategoryRoot = subquery.from(ProductCategoryEntity.class);
            Root<CategoryEntity> categoryRoot = subquery.from(CategoryEntity.class);

            String productIdField = StringUtils.kebabCaseToCamelCase(ProductCategoryField.PRODUCT_ID.getField());
            String categoryIdField = StringUtils.kebabCaseToCamelCase(ProductCategoryField.CATEGORY_ID.getField());

            List<Predicate> predicates = new ArrayList<>();
            // join product_category with category condition
            predicates.add(
                    builder.equal(
                            productCategoryRoot.get(categoryIdField), categoryRoot.get(CategoryField.ID.getField())
                    )
            );

            // filter by category path condition
            predicates.add(
                    builder.or(
                            builder.equal(
                                    categoryRoot.get(CategoryField.PATH.getField()), categoryPath
                            ),
                            builder.like(
                                    categoryRoot.get(CategoryField.PATH.getField()), categoryPath + "/%"
                            )
                    )
            );

            // filter by enable category
            if (filterEnableCategory) {
                predicates.add(builder.isNotNull(categoryRoot.get(CategoryField.DELETED.getField())));
            }

            subquery.select(productCategoryRoot.get(productIdField))
                    .where(predicates.toArray(new Predicate[0]));

            return builder.and(root.get(ProductField.ID.getField()).in(subquery));
        };
    }
}
