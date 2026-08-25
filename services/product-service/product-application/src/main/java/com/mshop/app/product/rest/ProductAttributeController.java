package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.dto.request.product.AttachAttributeValueRequest;
import com.mshop.app.product.dto.request.product.DetachProductAttributeValueRequest;
import com.mshop.app.product.service.ProductAttributeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("api/v1")
@RestController
@RequiredArgsConstructor
public class ProductAttributeController {

    private final ProductAttributeService service;

    @GetMapping("/products/{id}/attributes")
    public ApiResponse<List<AttributeValue>> getAllEnableAttributes(@PathVariable("id") String productId) {
        return ApiResponse.buildSuccessResponse("Successfully fetched attribute values from product",
                service.getAllEnabledAttributeValuesByProductId(productId));
    }

    @PostMapping("/admin/products/{id}/attributes")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> attachAttributeValue(@PathVariable("id") String productId,
                                                  @RequestBody @Valid AttachAttributeValueRequest request) {
        service.attachAttributeValuesToProduct(productId, request.getAttributeValueIds());
        return ApiResponse.buildSuccessResponse("Successfully added attribute values to product", null);
    }

    @DeleteMapping("/admin/products/{id}/attributes")
    public ApiResponse<Void> detachAttributeValue(@PathVariable("id") String productId,
                                                  @RequestBody @Valid DetachProductAttributeValueRequest request) {
        service.detachAttributeValueFromProduct(productId, request.getAttributeValueIds());
        return ApiResponse.buildSuccessResponse("Successfully removed attribute values from product", null);
    }

    @GetMapping("/admin/products/{id}/attributes")
    public ApiResponse<List<AttributeValue>> getAllAttributes(@PathVariable("id") String productId) {
        return ApiResponse.buildSuccessResponse("Successfully fetched attribute values from product",
                service.getAllAttributeValuesByProductId(productId));
    }
}
