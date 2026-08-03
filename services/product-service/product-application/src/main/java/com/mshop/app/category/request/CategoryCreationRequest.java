package com.mshop.app.category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreationRequest {

    @NotBlank(message = "CATEGORY_NAME_NOT_BLANK")
    private String name;
    @NotBlank(message = "CATEGORY_CODE_NOT_BLANK")
    private String code;
    private String parentId;
}
