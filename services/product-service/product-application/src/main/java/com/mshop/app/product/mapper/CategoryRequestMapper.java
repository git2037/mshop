package com.mshop.app.product.mapper;

import com.mshop.app.product.model.Category;
import com.mshop.app.product.request.category.CategoryCreationRequest;
import com.mshop.app.product.request.category.CategoryUpdatingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRequestMapper {
    Category toCategory(CategoryCreationRequest request);
    Category toCategory(CategoryUpdatingRequest request);
}
