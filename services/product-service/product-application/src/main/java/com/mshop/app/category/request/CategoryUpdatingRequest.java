package com.mshop.app.category.request;

import com.mshop.app.common.core.validator.NotBlankIfPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdatingRequest {
    @NotBlankIfPresent(message = "CODE_NOT_BLANK_IF_PRESENT")
    private String code;
    @NotBlankIfPresent(message = "NAME_NOT_BLANK_IF_PRESENT")
    private String name;
}
