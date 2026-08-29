package com.mshop.app.product.dto.request.category;

import com.mshop.app.common.core.validator.NotBlankIfPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMovingRequest {
    @NotBlankIfPresent(message = "CATEGORY_PARENT_ID_NOT_BLANK_IF_PRESENT")
    private String parentId;
}
