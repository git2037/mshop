package com.mshop.app.category.rest;

import com.mshop.app.category.mapper.CategoryRequestMapper;
import com.mshop.app.category.model.Category;
import com.mshop.app.category.request.CategoryCreationRequest;
import com.mshop.app.category.request.CategoryMovingRequest;
import com.mshop.app.category.request.CategoryUpdatingRequest;
import com.mshop.app.category.search.CategorySearchConfig;
import com.mshop.app.category.service.CategoryService;
import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRequestMapper categoryMapper;
    private final CategorySearchConfig searchConfig;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //admin
    public ApiResponse<Category> create(@RequestBody @Valid CategoryCreationRequest request) {
        Category category = categoryMapper.toCategory(request);
        log.info("Creating category with name={}, code={}", request.getName(), request.getCode());
        Category createdCategory = categoryService.create(category);
        log.info("Successfully create category");
        return ApiResponse.buildSuccessResponse("Create category successfully", createdCategory);
    }

    @GetMapping
    //admin
    public ApiResponse<List<Category>> getAll(@RequestParam(required = false, name = "sort") List<String> sort,
                                              @RequestParam Map<String, String> filter) {
        Query query = QueryParamParser.parseQueryParam(filter, sort, searchConfig);

        log.info("Fetching category list...");
        List<Category> users = categoryService.getAll(query);
        log.info("Successfully retrieved category list.");

        return ApiResponse.buildSuccessResponse("Categories fetched successfully", users);
    }

    @GetMapping("/roots")
    public ApiResponse<List<Category>> getAllRootCategories() {
        log.info("Fetching root categories...");
        List<Category> categories = categoryService.getAllRootCategories();
        log.info("Successfully fetched root categories");
        return ApiResponse.buildSuccessResponse("Root categories fetched successfully", categories);
    }

    @GetMapping("/{id}/children")
    public ApiResponse<List<Category>> getAllChildrenCategories(@PathVariable("id") String parentId) {
        log.info("Fetching children categories by parentId={}...", parentId);
        List<Category> categories = categoryService.getAllChildrenCategories(parentId);
        log.info("Successfully fetched children categories");
        return ApiResponse.buildSuccessResponse("Children categories fetched successfully", categories);
    }

    @GetMapping("/{id}/path")
    public ApiResponse<List<Category>> getPathToRoot(@PathVariable("id") String childId) {
        log.info("Fetching path to categories by childId={}...", childId);
        List<Category> categories = categoryService.getPathToRoot(childId);
        log.info("Successfully fetched path to categories");
        return ApiResponse.buildSuccessResponse("Path to categories fetched successfully", categories);
    }

    @GetMapping("/{id}")
    public ApiResponse<Category> getById(@PathVariable("id") String categoryId) {
        log.info("Fetching category by id {}", categoryId);
        Category category = categoryService.getCategoryById(categoryId);
        log.info("Successfully fetched category");
        return ApiResponse.buildSuccessResponse("Category successfully fetched", category);
    }

    @PutMapping("/{id}")
    //admin
    public ApiResponse<Category> update(@PathVariable("id") String categoryId, @RequestBody @Valid CategoryUpdatingRequest request) {
        log.info("Updating category with id={}...", categoryId);
        Category payload = categoryMapper.toCategory(request);
        Category updatedCategory = categoryService.update(categoryId, payload);
        log.info("Successfully updated category");
        return ApiResponse.buildSuccessResponse("Category successfully updated", updatedCategory);
    }

    @PostMapping("/{id}/move")
    //admin
    public ApiResponse<Void> moveTree(@PathVariable("id") String categoryId, @RequestBody @Valid CategoryMovingRequest request) {
        String parentId = request.getParentId();
        log.info("Moving category with id={} to parent id={}", categoryId, parentId);
        categoryService.move(categoryId, parentId);
        log.info("Successfully moved category");
        return ApiResponse.buildSuccessResponse("Category successfully move", null);
    }

    @DeleteMapping("/{id}")
    //admin
    public ApiResponse<Void> disable(@PathVariable("id") String categoryId) {
        log.info("Soft deleting category with id={}", categoryId);
        categoryService.disable(categoryId);
        log.info("Successfully deleted category");
        return ApiResponse.buildSuccessResponse("Category successfully deleted", null);
    }

    @PostMapping("/{id}")
    //admin
    public ApiResponse<Void> enable(@PathVariable("id") String categoryId) {
        log.info("Enabling category with id={}", categoryId);
        categoryService.enable(categoryId);
        log.info("Successfully enabled category");
        return ApiResponse.buildSuccessResponse("Category successfully enabled", null);
    }
}
