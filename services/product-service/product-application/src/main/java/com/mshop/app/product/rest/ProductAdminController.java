package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.SearchConfig;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
import com.mshop.app.product.mapper.ProductRequestMapper;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.request.product.AddProductCategoryRequest;
import com.mshop.app.product.request.product.AttachAttributeValueRequest;
import com.mshop.app.product.request.product.CreateProductRequest;
import com.mshop.app.product.request.product.DetachProductAttributeValueRequest;
import com.mshop.app.product.request.product.RemoveProductCategoryRequest;
import com.mshop.app.product.request.product.UpdateProductRequest;
import com.mshop.app.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("api/v1/admin/products")
@RestController
public class ProductAdminController {

    private final ProductService service;
    private final ProductRequestMapper mapper;
    private final SearchConfig searchConfig;

    public ProductAdminController(ProductService service,
                                  ProductRequestMapper mapper,
                                  @Qualifier("productAdminSearchConfig") SearchConfig searchConfig
    ) {
        this.service = service;
        this.mapper = mapper;
        this.searchConfig = searchConfig;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Product> create(@RequestBody @Valid CreateProductRequest request) {
        Product product = mapper.toProduct(request);
        return ApiResponse.buildSuccessResponse("Create product successfully",
                service.create(product));
    }

    @GetMapping
    public ApiResponse<List<Product>> getAll(@RequestParam(required = false, name = "sort") List<String> sort,
                                             @RequestParam Map<String, String> filter) {
        Query query = QueryParamParser.parseQueryParam(filter, sort, searchConfig);

        log.debug("Get products by query:{}", query);
        return ApiResponse.buildSuccessResponse("Products fetched successfully",
                service.getAll(query));
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> getById(@PathVariable("id") String productId) {
        return ApiResponse.buildSuccessResponse("Product fetched successfully",
                service.getById(productId));
    }

    @PutMapping("/{id}")
    public ApiResponse<Product> update(@PathVariable("id") String productId,
                                       @RequestBody @Valid UpdateProductRequest request) {
        Product product = mapper.toProduct(request);
        product.setId(productId);
        return ApiResponse.buildSuccessResponse("Product updated successfully",
                service.update(product));
    }

    @PostMapping("/{id}/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> addProductToCategories(@PathVariable("id") String productId,
                                                    @RequestBody @Valid AddProductCategoryRequest request) {
        service.addToCategories(productId, request.getCategoryIds());
        return ApiResponse.buildSuccessResponse("Product added to categories successfully", null);
    }

    @PutMapping("/{id}/categories")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> removeProductToCategories(@PathVariable("id") String productId,
                                                    @RequestBody @Valid RemoveProductCategoryRequest request) {
        service.removeFromCategories(productId, request.getCategoryIds());
        return ApiResponse.buildSuccessResponse("Product removed to categories successfully", null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> disable(@PathVariable("id") String productId) {
        service.disable(productId);
        return ApiResponse.buildSuccessResponse("Product disabled successfully", null);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> enable(@PathVariable("id") String productId) {
        service.enable(productId);
        return ApiResponse.buildSuccessResponse("Product enabled successfully", null);
    }

    @PostMapping("/{id}/attributes")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> attachAttributeValue(@PathVariable("id") String productId,
                                                    @RequestBody @Valid AttachAttributeValueRequest request) {
        service.attachAttributeValue(productId, request.getAttributeValueIds());
        return ApiResponse.buildSuccessResponse("Successfully added attribute values to product", null);
    }

    @DeleteMapping("/{id}/attributes")
    public ApiResponse<Void> detachAttributeValue(@PathVariable("id") String productId,
                                                @RequestBody @Valid DetachProductAttributeValueRequest request) {
        service.detachAttributeValue(productId, request.getAttributeValueIds());
        return ApiResponse.buildSuccessResponse("Successfully removed attribute values from product", null);
    }

    @GetMapping("/{id}/attributes")
    public ApiResponse<List<AttributeValue>> getAllAttributes(@PathVariable("id") String productId) {
        return ApiResponse.buildSuccessResponse("Successfully fetched attribute values from product",
                service.getAllAttributeValuesById(productId));
    }
}
