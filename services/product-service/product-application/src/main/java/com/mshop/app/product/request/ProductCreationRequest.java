package com.mshop.app.product.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationRequest {
    @NotBlank(message = "PRODUCT_NAME_NOT_BLANK")
    private String name;

    private String description;

    private Set<String> categoryIds;
}
