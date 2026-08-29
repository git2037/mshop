package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.product.dto.request.sku.CreateSkuRequest;
import com.mshop.app.product.dto.response.AttributeValueResponse;
import com.mshop.app.product.dto.response.SkuResponse;
import com.mshop.app.product.mapper.SkuRequestMapper;
import com.mshop.app.product.model.Sku;
import com.mshop.app.product.model.SkuAttributeValue;
import com.mshop.app.product.service.SkuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/v1")
@RestController
@RequiredArgsConstructor
public class SkuController {

    private final SkuService skuService;
    private final SkuRequestMapper skuMapper;

    @PostMapping("/admin/products/{id}/skus")
    public ApiResponse<Sku> createSku(@Valid @RequestBody CreateSkuRequest request,
                                      @PathVariable("id") String productId) {
        Sku sku = skuMapper.toSku(request, productId);
        return ApiResponse.buildSuccessResponse("Created sku successfully",
                skuService.createSku(sku, request.getAttributeValueIds()));
    }

    @GetMapping("/admin/products/{id}/skus")
    public ApiResponse<List<SkuResponse>> getSkus(@PathVariable("id") String productId) {
        List<SkuAttributeValue> skuAttributeValues = skuService.getAllByProductId(productId);

        return ApiResponse.buildSuccessResponse("Fetched sku list successfully",
                toSkuResponse(skuAttributeValues));
    }

    @GetMapping("/products/{id}/skus")
    public ApiResponse<List<SkuResponse>> getEnableSkus(@PathVariable("id") String productId) {
        List<SkuAttributeValue> skuAttributeValues = skuService.getAllEnableSkuByProductId(productId);

        return ApiResponse.buildSuccessResponse("Fetched sku list successfully",
                toSkuResponse(skuAttributeValues));
    }

    @GetMapping("/admin/skus/{id}")
    public ApiResponse<List<SkuResponse>> getSku(@PathVariable("id") String skuId) {
        List<SkuAttributeValue> skuAttributeValues = skuService.getById(skuId);

        return ApiResponse.buildSuccessResponse("Fetched sku successfully",
                toSkuResponse(skuAttributeValues));
    }

    @DeleteMapping("/admin/skus/{id}")
    public ApiResponse<Void> disable(@PathVariable("id") String skuId) {
        skuService.disable(skuId);
        return ApiResponse.buildSuccessResponse("Disabled sku successfully", null);
    }

    @PatchMapping("/admin/skus/{id}")
    public ApiResponse<Void> enable(@PathVariable("id") String skuId) {
        skuService.enable(skuId);
        return ApiResponse.buildSuccessResponse("Enabled sku successfully", null);
    }

    private List<SkuResponse> toSkuResponse(List<SkuAttributeValue> skuAttributeValues) {
        return skuAttributeValues.stream()
                .collect(Collectors.groupingBy(SkuAttributeValue::getSkuCode))
                .values()
                .stream()
                .map(values -> {
                    List<AttributeValueResponse> attributeValueResponses = values.stream()
                            .map(skuMapper::toAttributeValueResponse)
                            .toList();

                    return skuMapper.toSkuResponse(values.get(0), attributeValueResponses);
                })
                .toList();
    }
}
