package com.mshop.app.product.dto.request.product.image;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttachImagesRequest {

    @NotEmpty(message = "PRODUCT_IMAGES_NOT_NULL")
    private List<MultipartFile> files;
}
