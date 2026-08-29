package com.mshop.app.product.dto.request.product.image;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SetThumbnailRequest {

    @NotBlank(message = "PRODUCT_IMAGE_FILE_NAME_NOT_BLANK")
    private String fileName;
}
