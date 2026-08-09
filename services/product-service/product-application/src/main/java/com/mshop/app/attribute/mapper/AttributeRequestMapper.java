package com.mshop.app.attribute.mapper;

import com.mshop.app.attribute.model.Attribute;
import com.mshop.app.attribute.request.CreateAttributeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeRequestMapper {
    Attribute toAttribute(CreateAttributeRequest request);
}
