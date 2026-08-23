package com.mshop.app.product.mapper;

import com.mshop.app.product.model.Sku;
import com.mshop.app.product.request.sku.CreateSkuRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkuRequestMapper {
    @Mapping(target = "productId", source = "productId")
    Sku toSku(CreateSkuRequest request, String productId);
}
