package com.mshop.app.product.mapper;

import com.mshop.app.common.core.mapper.BaseMapper;
import com.mshop.app.product.jpa.entity.CategoryEntity;
import com.mshop.app.product.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper extends BaseMapper<CategoryEntity, Category> {
}
