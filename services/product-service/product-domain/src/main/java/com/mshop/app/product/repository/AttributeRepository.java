package com.mshop.app.product.repository;

import com.mshop.app.product.model.Attribute;
import com.mshop.app.common.core.searching.model.Query;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository {
    Attribute save(Attribute attribute);

    List<Attribute> findAll(Query query);

    Optional<Attribute> findById(String attributeId);
}
