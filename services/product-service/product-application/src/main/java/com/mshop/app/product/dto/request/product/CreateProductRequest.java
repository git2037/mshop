package com.mshop.app.product.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {
    @NotBlank(message = "PRODUCT_NAME_NOT_BLANK")
    private String name;

    private String description;

    @NotBlank(message = "PRODUCT_CODE_NOT_BLANK")
    private String code;
}
