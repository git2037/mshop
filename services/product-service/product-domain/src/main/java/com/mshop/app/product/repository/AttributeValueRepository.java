package com.mshop.app.product.repository;

import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.product.model.AttributeValue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AttributeValueRepository {
    AttributeValue save(AttributeValue attributeValue);

    List<AttributeValue> findAllByAttributeCode(String attributeCode, Pagination pagination);

    Optional<AttributeValue> findById(String attributeValueId);

    void disableAllByAttributeCode(String attributeCode);

    void enableAllByAttributeCode(String attributeCode);

    Set<AttributeValue> findAllByIdIn(Set<String> attributeValueIds);
}
