package com.mshop.app.product.mapper;

import com.mshop.app.common.core.mapper.BaseMapper;
import com.mshop.app.product.jpa.entity.ProductEntity;
import com.mshop.app.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper extends BaseMapper<ProductEntity, Product> {
}
