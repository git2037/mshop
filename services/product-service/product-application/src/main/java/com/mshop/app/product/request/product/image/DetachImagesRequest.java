package com.mshop.app.product.request.product.image;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DetachImagesRequest {

    @NotEmpty(message = "PRODUCT_IMAGE_FILE_NAMES_NOT_NULL")
    private Set<
            @NotBlank(message = "PRODUCT_IMAGE_FILE_NAME_NOT_BLANK")
            String> fileNames;
}
