package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.common.core.searching.parser.PaginationParser;
import com.mshop.app.product.mapper.AttributeValueRequestMapper;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.request.attribute.CreateAttributeValueRequest;
import com.mshop.app.product.request.attribute.UpdateAttributeValueRequest;
import com.mshop.app.product.service.AttributeValueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RequestMapping("api/v1/admin/attributes")
@RestController
@RequiredArgsConstructor
public class AttributeValueController {

    private final AttributeValueService attributeValueService;
    private final AttributeValueRequestMapper attributeValueRequestMapper;

    @PostMapping("/{code}/values")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AttributeValue> createAttributeValue(@PathVariable("code") String attributeCode,
                                                            @RequestBody @Valid CreateAttributeValueRequest request) {
        AttributeValue attributeValue = attributeValueRequestMapper
                .toAttributeValue(request, attributeCode.toUpperCase());
        return ApiResponse.buildSuccessResponse("Created attribute value successfully",
                attributeValueService.create(attributeValue));
    }

    @GetMapping("/{code}/values")
    public ApiResponse<List<AttributeValue>> getAttributeValuesByAttributeCode(@PathVariable("code") String attributeCode,
                                                                               @RequestParam Map<String, String> queryParams) {
        Pagination pagination = PaginationParser.parse(queryParams);
        return ApiResponse.buildSuccessResponse("Fetched attribute values successfully",
                attributeValueService.getAllByAttributeCode(attributeCode, pagination));
    }

    @GetMapping("/values/{id}")
    public ApiResponse<AttributeValue> getAttributeValue(@PathVariable("id") String attributeValueId) {
        return ApiResponse.buildSuccessResponse("Fetched attribute value successfully",
                attributeValueService.getById(attributeValueId));
    }

    @PutMapping("/values/{id}")
    public ApiResponse<AttributeValue> update(@PathVariable("id") String attributeValueId,
                                              @RequestBody @Valid UpdateAttributeValueRequest request) {
        AttributeValue attributeValue = attributeValueRequestMapper
                .toAttributeValue(request, attributeValueId);

        return ApiResponse.buildSuccessResponse("Updated attribute value successfully",
                attributeValueService.update(attributeValue));
    }

    @DeleteMapping("/values/{id}")
    public ApiResponse<Void> disable(@PathVariable("id") String attributeValueId) {
        attributeValueService.disable(attributeValueId);
        return ApiResponse.buildSuccessResponse("Disabled attribute successfully", null);
    }

    @PatchMapping("/values/{id}")
    public ApiResponse<Void> enable(@PathVariable("id") String attributeValueId) {
        attributeValueService.enable(attributeValueId);
        return ApiResponse.buildSuccessResponse("Enabled attribute successfully", null);
    }
}
