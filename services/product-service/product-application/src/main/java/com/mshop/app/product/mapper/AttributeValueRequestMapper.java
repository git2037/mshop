package com.mshop.app.product.mapper;

import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.request.attribute.CreateAttributeValueRequest;
import com.mshop.app.product.request.attribute.UpdateAttributeValueRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeValueRequestMapper {
    @Mapping(target = "attributeCode", source = "attributeCode")
    @Mapping(target = "value", source = "request.value")
    AttributeValue toAttributeValue(CreateAttributeValueRequest request, String attributeCode);

    AttributeValue toAttributeValue(UpdateAttributeValueRequest request, String id);
}
