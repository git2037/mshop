package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.product.mapper.SkuRequestMapper;
import com.mshop.app.product.model.Sku;
import com.mshop.app.product.request.sku.CreateSkuRequest;
import com.mshop.app.product.service.SkuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/products/{id}/skus")
@RestController
@RequiredArgsConstructor
public class SkuController {

    private final SkuService skuService;
    private final SkuRequestMapper skuMapper;

    @PostMapping
    public ApiResponse<Sku> createSku(@Valid @RequestBody CreateSkuRequest request,
                                      @PathVariable("id") String productId) {
        Sku sku = skuMapper.toSku(request, productId);
        return ApiResponse.buildSuccessResponse("Created sku successfully",
                skuService.createSku(sku, request.getAttributeValueIds()));
    }
}
