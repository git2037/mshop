package com.mshop.app.product.mapper;

import com.mshop.app.product.model.Product;
import com.mshop.app.product.request.ProductCreationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductRequestMapper {
    Product toProduct(ProductCreationRequest request);
}
