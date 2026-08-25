package com.mshop.app.product.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateProductRequest {
    @NotBlank(message = "PRODUCT_NAME_NOT_BLANK")
    private String name;

    private String description;
}
