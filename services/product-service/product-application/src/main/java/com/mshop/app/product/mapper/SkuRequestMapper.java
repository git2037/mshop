package com.mshop.app.product.mapper;

import com.mshop.app.product.dto.request.sku.CreateSkuRequest;
import com.mshop.app.product.dto.response.AttributeValueResponse;
import com.mshop.app.product.dto.response.SkuResponse;
import com.mshop.app.product.model.Sku;
import com.mshop.app.product.model.SkuAttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuRequestMapper {
    @Mapping(target = "productId", source = "productId")
    Sku toSku(CreateSkuRequest request, String productId);

    @Mapping(target = "id", source = "skuAttributeValue.skuId")
    @Mapping(target = "code", source = "skuAttributeValue.skuCode")
    @Mapping(target = "attributes", source = "attributeValueResponse")
    SkuResponse toSkuResponse(SkuAttributeValue skuAttributeValue, List<AttributeValueResponse> attributeValueResponse);

    @Mapping(target = "code", source = "attributeCode")
    @Mapping(target = "name", source = "attributeName")
    @Mapping(target = "value", source = "attributeValue")
    AttributeValueResponse toAttributeValueResponse(SkuAttributeValue skuAttributeValue);
}
