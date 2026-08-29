package com.mshop.app.product.mapper;

import com.mshop.app.product.model.Attribute;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.dto.request.attribute.CreateAttributeRequest;
import com.mshop.app.product.dto.request.attribute.CreateAttributeValueRequest;
import com.mshop.app.product.dto.request.attribute.UpdateAttributeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeRequestMapper {
    Attribute toAttribute(CreateAttributeRequest request);

    Attribute toAttribute(UpdateAttributeRequest request);

    @Mapping(target = "attributeCode", source = "attributeCode")
    @Mapping(target = "value", source = "request.value")
    AttributeValue toAttributeValue(CreateAttributeValueRequest request, String attributeCode);
}
