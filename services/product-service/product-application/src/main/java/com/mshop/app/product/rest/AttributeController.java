package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.common.core.searching.model.Query;
import com.mshop.app.common.core.searching.parser.QueryParamParser;
import com.mshop.app.product.mapper.AttributeRequestMapper;
import com.mshop.app.product.model.Attribute;
import com.mshop.app.product.dto.request.attribute.CreateAttributeRequest;
import com.mshop.app.product.dto.request.attribute.UpdateAttributeRequest;
import com.mshop.app.product.search.AttributeSearchConfig;
import com.mshop.app.product.service.AttributeService;
import com.mshop.app.security.anotation.IsAdmin;
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
@IsAdmin
public class AttributeController {

    private final AttributeService attributeService;
    private final AttributeRequestMapper attributeRequestMapper;
    private final AttributeSearchConfig attributeSearchConfig;

    public AttributeController(AttributeService attributeService,
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
    public ApiResponse<Void> disable(@PathVariable("id") String attributeId) {
        attributeService.disable(attributeId);
        return ApiResponse.buildSuccessResponse("Disabled attribute successfully", null);
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> enable(@PathVariable("id") String attributeId) {
        attributeService.enable(attributeId);
        return ApiResponse.buildSuccessResponse("Enabled attribute successfully", null);
    }
}
