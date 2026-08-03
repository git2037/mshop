package com.mshop.app.category.mapper;

import com.mshop.app.category.model.Category;
import com.mshop.app.category.request.CategoryCreationRequest;
import com.mshop.app.category.request.CategoryUpdatingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRequestMapper {
    Category toCategory(CategoryCreationRequest request);
    Category toCategory(CategoryUpdatingRequest request);
}
