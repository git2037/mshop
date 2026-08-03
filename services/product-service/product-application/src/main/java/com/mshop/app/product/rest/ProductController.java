package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.product.mapper.ProductRequestMapper;
import com.mshop.app.product.model.Product;
import com.mshop.app.product.request.ProductCreationRequest;
import com.mshop.app.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/products")
@RestController
public class ProductController {

    private final ProductService service;
    private final ProductRequestMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //admin
    public ApiResponse<Product> create(@RequestBody @Valid ProductCreationRequest request) {
        Product product = mapper.toProduct(request);
        log.info("Creating product with name={}...", request.getName());
        Product createdProduct = service.create(product);
        log.info("Successfully create product");
        return ApiResponse.buildSuccessResponse("Create product successfully", createdProduct);
    }
}
