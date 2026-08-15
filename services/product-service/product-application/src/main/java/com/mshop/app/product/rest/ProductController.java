package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.SearchConfig;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("api/v1/products")
@RestController
public class ProductController {

    private final ProductService service;
    private final SearchConfig searchConfig;

    public ProductController(ProductService service,
                             @Qualifier("productSearchConfig") SearchConfig searchConfig
    ) {
        this.service = service;
        this.searchConfig = searchConfig;
    }

    @GetMapping
    public ApiResponse<List<Product>> getAll(@RequestParam(required = false, name = "sort") List<String> sort,
                                              @RequestParam Map<String, String> filter) {
        Query query = QueryParamParser.parseQueryParam(filter, sort, searchConfig);

        log.debug("Get products by query: {}", query);
        return ApiResponse.buildSuccessResponse("Products fetched successfully",
                service.getAllEnableProduct(query));
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> getById(@PathVariable("id") String productId) {
        return ApiResponse.buildSuccessResponse("Product fetched successfully",
                service.getEnableProductById(productId));
    }

    @GetMapping("/{id}/attributes")
    public ApiResponse<List<AttributeValue>> getAllAttributes(@PathVariable("id") String productId) {
        return ApiResponse.buildSuccessResponse("Successfully fetched attribute values from product",
                service.getAllEnabledAttributeValuesById(productId));
    }
}
