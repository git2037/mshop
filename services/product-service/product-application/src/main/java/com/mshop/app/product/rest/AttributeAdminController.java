package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.model.Pagination;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.PaginationParser;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
import com.mshop.app.product.mapper.AttributeRequestMapper;
import com.mshop.app.product.model.Attribute;
import com.mshop.app.product.model.AttributeValue;
import com.mshop.app.product.request.attribute.CreateAttributeRequest;
import com.mshop.app.product.request.attribute.CreateAttributeValueRequest;
import com.mshop.app.product.request.attribute.UpdateAttributeRequest;
import com.mshop.app.product.search.AttributeSearchConfig;
import com.mshop.app.product.service.AttributeService;
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
@RequestMapping("api/v1/admin/attributes")
@RestController
public class AttributeAdminController {

    private final AttributeService attributeService;
    private final AttributeRequestMapper attributeRequestMapper;
    private final AttributeSearchConfig attributeSearchConfig;

    public AttributeAdminController(AttributeService attributeService,
                                    AttributeRequestMapper attributeRequestMapper,
                                    @Qualifier("attributeSearchConfig") AttributeSearchConfig attributeSearchConfig) {
        this.attributeService = attributeService;
        this.attributeRequestMapper = attributeRequestMapper;
        this.attributeSearchConfig = attributeSearchConfig;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Attribute> create(@RequestBody @Valid CreateAttributeRequest request) {
        Attribute attribute = attributeRequestMapper.toAttribute(request);
        return ApiResponse.buildSuccessResponse("Created attribute successfully",
                attributeService.create(attribute));
    }

    @GetMapping
    public ApiResponse<List<Attribute>> getAttributes(@RequestParam(required = false, name = "sort") List<String> sort,
                                                      @RequestParam Map<String, String> filter) {
        Query query = QueryParamParser.parseQueryParam(filter, sort, attributeSearchConfig);

        log.debug("Get attributes with query: {}", query);
        return ApiResponse.buildSuccessResponse("Attributes fetched successfully",
                attributeService.getAttributes(query));
    }

    @GetMapping("/{id}")
    public ApiResponse<Attribute> getAttributeById(@PathVariable("id") String attributeId) {
        return ApiResponse.buildSuccessResponse("Attribute successfully fetched",
                attributeService.getAttributeById(attributeId));
    }

    @PutMapping("/{id}")
    public ApiResponse<Attribute> update(@PathVariable("id") String attributeId,
                                         @RequestBody @Valid UpdateAttributeRequest request) {
        Attribute attribute = attributeRequestMapper.toAttribute(request);
        attribute.setId(attributeId);
        return ApiResponse.buildSuccessResponse("Updated attribute successfully",
                attributeService.update(attribute));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> disable(@PathVariable("id") String attributeId) {
        attributeService.disable(attributeId);
        return ApiResponse.buildSuccessResponse("Disabled attribute successfully", null);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> enable(@PathVariable("id") String attributeId) {
        attributeService.enable(attributeId);
        return ApiResponse.buildSuccessResponse("Enabled attribute successfully", null);
    }

    @PostMapping("/{code}/values")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AttributeValue> createAttributeValue(@PathVariable("code") String attributeCode,
                                                            @RequestBody @Valid CreateAttributeValueRequest request) {
        AttributeValue attributeValue = attributeRequestMapper
                .toAttributeValue(request, attributeCode.toUpperCase());
        return ApiResponse.buildSuccessResponse("Created attribute value successfully",
                attributeService.createAttributeValue(attributeValue));
    }

    @GetMapping("/{code}/values")
    public ApiResponse<List<AttributeValue>> getAttributeValuesByAttributeCode(@PathVariable("code") String attributeCode,
                                                                               @RequestParam Map<String, String> queryParams) {
        Pagination pagination = PaginationParser.parse(queryParams);
        return ApiResponse.buildSuccessResponse("Fetched attribute values successfully",
                attributeService.getAttributeValuesByAttributeCode(attributeCode, pagination));
    }

    @GetMapping("/values/{id}")
    public ApiResponse<AttributeValue> getAttributeValue(@PathVariable("id") String attributeValueId) {
        return ApiResponse.buildSuccessResponse("Fetched attribute value successfully",
                attributeService.getAttributeValueByAttributeValueId(attributeValueId));
    }
}
