package com.mshop.app.attribute.service;

import com.mshop.app.attribute.model.Attribute;
import com.mshop.app.common.core.searching.model.Query;

import java.util.List;

public interface AttributeService {
    Attribute create(Attribute attribute);

    List<Attribute> getAttributes(Query query);

    Attribute getAttributeById(String attributeId);
}