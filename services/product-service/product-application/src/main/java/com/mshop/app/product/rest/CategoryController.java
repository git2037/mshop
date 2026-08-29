package com.mshop.app.product.rest;

import com.mshop.app.product.mapper.CategoryRequestMapper;
import com.mshop.app.product.model.Category;
import com.mshop.app.product.dto.request.category.CategoryCreationRequest;
import com.mshop.app.product.dto.request.category.CategoryMovingRequest;
import com.mshop.app.product.dto.request.category.CategoryUpdatingRequest;
import com.mshop.app.product.search.CategorySearchConfig;
import com.mshop.app.product.service.CategoryService;
import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("api/v1/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRequestMapper categoryMapper;
    private final CategorySearchConfig searchConfig;

    public CategoryController(CategoryService categoryService,
                              CategoryRequestMapper categoryMapper,
                              @Qualifier("categorySearchConfig") CategorySearchConfig searchConfig
    ) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.searchConfig = searchConfig;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //admin
    public ApiResponse<Category> create(@RequestBody @Valid CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);
        Category createdCategory = categoryService.create(category);
        return ApiResponse.buildSuccessResponse("Create category successfully", createdCategory);
    }

    @GetMapping
    //admin
    public ApiResponse<List<Category>> getAll(@RequestParam(required = false, name = "sort") List<String> sort,
                                              @RequestParam Map<String, String> filter) {
        Query query = QueryParamParser.parseQueryParam(filter, sort, searchConfig);

        log.debug("Get categories with query: {}", query);
        List<Category> categories = categoryService.getAll(query);
        return ApiResponse.buildSuccessResponse("Categories fetched successfully", categories);
    }

    @GetMapping("/roots")
    public ApiResponse<List<Category>> getAllRootCategories() {
        List<Category> categories = categoryService.getAllRootCategories();
        return ApiResponse.buildSuccessResponse("Root categories fetched successfully", categories);
    }

    @GetMapping("/{id}/children")
    public ApiResponse<List<Category>> getAllChildrenCategories(@PathVariable("id") String parentId) {
        List<Category> categories = categoryService.getAllChildrenCategories(parentId);
        return ApiResponse.buildSuccessResponse("Children categories fetched successfully", categories);
    }

    @GetMapping("/{id}/path")
    public ApiResponse<List<Category>> getPathToRoot(@PathVariable("id") String childId) {
        List<Category> categories = categoryService.getPathToRoot(childId);
        return ApiResponse.buildSuccessResponse("Path to categories fetched successfully", categories);
    }

    @GetMapping("/{id}")
    public ApiResponse<Category> getById(@PathVariable("id") String categoryId) {
        Category category = categoryService.getById(categoryId);
        return ApiResponse.buildSuccessResponse("Category successfully fetched", category);
    }

    @PutMapping("/{id}")
    //admin
    public ApiResponse<Category> update(@PathVariable("id") String categoryId,
                                        @RequestBody @Valid CategoryUpdatingRequest request) {
        Category payload = categoryMapper.toCategory(request);
        Category updatedCategory = categoryService.update(categoryId, payload);
        return ApiResponse.buildSuccessResponse("Category successfully updated", updatedCategory);
    }

    @PostMapping("/{id}/move")
    //admin
    public ApiResponse<Void> moveTree(@PathVariable("id") String categoryId,
                                      @RequestBody @Valid CategoryMovingRequest request) {
        String parentId = request.getParentId();
        categoryService.move(categoryId, parentId);
        return ApiResponse.buildSuccessResponse("Category successfully move", null);
    }

    @DeleteMapping("/{id}")
    //admin
    public ApiResponse<Void> disable(@PathVariable("id") String categoryId) {
        categoryService.disable(categoryId);
        return ApiResponse.buildSuccessResponse("Category successfully deleted", null);
    }

    @PostMapping("/{id}")
    //admin
    public ApiResponse<Void> enable(@PathVariable("id") String categoryId) {
        categoryService.enable(categoryId);
        return ApiResponse.buildSuccessResponse("Category successfully enabled", null);
    }
}
