package com.mshop.app.product.mapper;

import com.mshop.app.product.dto.request.category.UpdateCategoryRequest;
import com.mshop.app.product.model.Category;
import com.mshop.app.product.dto.request.category.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRequestMapper {
    Category toCategory(CreateCategoryRequest request);
    Category toCategory(UpdateCategoryRequest request);
}
