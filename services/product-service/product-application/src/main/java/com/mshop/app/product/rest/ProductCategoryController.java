package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.product.request.product.AddProductCategoryRequest;
import com.mshop.app.product.request.product.RemoveProductCategoryRequest;
import com.mshop.app.product.service.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/v1/admin/products/{id}/categories")
@RestController
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> attachProduct(@PathVariable("id") String productId,
                                                    @RequestBody @Valid AddProductCategoryRequest request) {
        service.attachProduct(productId, request.getCategoryIds());
        return ApiResponse.buildSuccessResponse("Product added to categories successfully", null);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> detachProduct(@PathVariable("id") String productId,
                                                       @RequestBody @Valid RemoveProductCategoryRequest request) {
        service.detachProduct(productId, request.getCategoryIds());
        return ApiResponse.buildSuccessResponse("Product removed to categories successfully", null);
    }
}
