package com.mshop.app.product.dto.request.category;

import com.mshop.app.common.core.validator.NotBlankIfPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdatingRequest {
    @NotBlankIfPresent(message = "CATEGORY_CODE_NOT_BLANK_IF_PRESENT")
    private String code;
    @NotBlankIfPresent(message = "CATEGORY_NAME_NOT_BLANK_IF_PRESENT")
    private String name;
}
