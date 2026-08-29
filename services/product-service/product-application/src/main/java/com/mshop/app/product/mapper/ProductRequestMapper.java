package com.mshop.app.product.mapper;

import com.mshop.app.product.model.Product;
import com.mshop.app.product.dto.request.product.CreateProductRequest;
import com.mshop.app.product.dto.request.product.UpdateProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductRequestMapper {
    Product toProduct(CreateProductRequest request);

    Product toProduct(UpdateProductRequest request);
}
