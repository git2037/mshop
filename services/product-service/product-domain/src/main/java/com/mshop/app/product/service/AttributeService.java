package com.mshop.app.product.service;

import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.product.model.Attribute;

import java.util.List;

public interface AttributeService {
    Attribute create(Attribute attribute);

    List<Attribute> getAttributes(Query query);

    Attribute getAttributeById(String attributeId);

    Attribute update(Attribute attribute);

    void disable(String attributeId);

    void enable(String attributeId);
}