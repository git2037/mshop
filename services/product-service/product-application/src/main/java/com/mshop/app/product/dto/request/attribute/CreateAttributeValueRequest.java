package com.mshop.app.product.dto.request.attribute;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAttributeValueRequest {

    @NotBlank(message = "ATTRIBUTE_VALUE_NOT_BLANK")
    private String value;

    @NotBlank(message = "ATTRIBUTE_VALUE_CODE_NOT_BLANK")
    private String code;
}
