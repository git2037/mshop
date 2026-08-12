package com.mshop.app.product.service;

import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.product.model.AttributeValue;

import java.util.List;

public interface AttributeValueService {

    AttributeValue create(AttributeValue attributeValue);

    List<AttributeValue> getAllByAttributeCode(String attributeCode, Pagination pagination);

    AttributeValue getById(String attributeValueId);

    AttributeValue update(AttributeValue attributeValue);

    void disable(String attributeValueId);

    void enable(String attributeValueId);
}