package com.mshop.app.product.request.attribute;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttributeValueRequest {

    @NotBlank(message = "ATTRIBUTE_VALUE_NOT_BLANK")
    private String value;
}
