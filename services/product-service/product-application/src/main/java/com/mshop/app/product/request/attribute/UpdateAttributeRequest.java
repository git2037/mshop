package com.mshop.app.product.request.attribute;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttributeRequest {

    @NotBlank(message = "ATTRIBUTE_NAME_NOT_BLANK")
    private String name;
}
