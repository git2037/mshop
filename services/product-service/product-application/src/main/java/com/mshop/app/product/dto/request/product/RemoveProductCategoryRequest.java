package com.mshop.app.product.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RemoveProductCategoryRequest {
    @NotEmpty(message = "CATEGORY_IDS_NOT_EMPTY")
    private Set<
            @NotBlank(message = "CATEGORY_ID_NOT_BLANK")
            String> categoryIds;
}
