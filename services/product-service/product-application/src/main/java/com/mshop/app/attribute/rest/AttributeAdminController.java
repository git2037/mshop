package com.mshop.app.attribute.rest;

import com.mshop.app.attribute.mapper.AttributeRequestMapper;
import com.mshop.app.attribute.model.Attribute;
import com.mshop.app.attribute.request.CreateAttributeRequest;
import com.mshop.app.attribute.service.AttributeService;
import com.mshop.app.common.core.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/v1/admin/attributes")
@RestController
@RequiredArgsConstructor
public class AttributeAdminController {

    private final AttributeService attributeService;
    private final AttributeRequestMapper attributeRequestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Attribute> create(@RequestBody @Valid CreateAttributeRequest request) {
        Attribute attribute = attributeRequestMapper.toAttribute(request);
        return ApiResponse.buildSuccessResponse("Create attribute successfully",
                attributeService.create(attribute));
    }

}
