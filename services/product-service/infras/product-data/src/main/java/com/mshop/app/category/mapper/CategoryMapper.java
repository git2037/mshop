package com.mshop.app.category.mapper;

import com.mshop.app.common.core.mapper.BaseMapper;
import com.mshop.app.category.jpa.entity.CategoryEntity;
import com.mshop.app.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper extends BaseMapper<CategoryEntity, Category> {
}
