package com.mshop.app.product.mapper;

import com.mshop.app.product.model.Attribute;
import com.mshop.app.product.request.attribute.CreateAttributeRequest;
import com.mshop.app.product.request.attribute.UpdateAttributeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeRequestMapper {
    Attribute toAttribute(CreateAttributeRequest request);

    Attribute toAttribute(UpdateAttributeRequest request);
}
