package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.SearchConfig;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
import com.mshop.app.product.mapper.ProductRequestMapper;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.request.ProductCreationRequest;
import com.mshop.app.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ApiResponse<Product> create(@RequestBody @Valid ProductCreationRequest request) {
        Product product = mapper.toProduct(request);
        return ApiResponse.buildSuccessResponse("Create product successfully",
                service.create(product, request.getCategoryIds()));
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
}
