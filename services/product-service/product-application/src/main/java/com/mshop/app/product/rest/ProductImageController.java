package com.mshop.app.product.rest;

import com.mshop.app.common.core.response.ApiResponse;
import com.mshop.app.product.dto.request.product.image.AttachImagesRequest;
import com.mshop.app.product.dto.request.product.image.DetachImagesRequest;
import com.mshop.app.product.dto.request.product.image.SetThumbnailRequest;
import com.mshop.app.product.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/products/{id}")
@RestController
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<Void> attachImages(@Valid @ModelAttribute AttachImagesRequest request,
                                          @PathVariable("id") String productId) {
        productImageService.create(productId, request.getFiles());
        return ApiResponse.buildSuccessResponse("Attached images to product successfully", null);
    }

    @DeleteMapping("/images")
    public ApiResponse<Void> detachImages(@Valid @RequestBody DetachImagesRequest request,
                                          @PathVariable("id") String productId) {
        productImageService.remove(productId, request.getFileNames());
        return ApiResponse.buildSuccessResponse("Detached images to product successfully", null);
    }

    @GetMapping("/images")
    public ApiResponse<List<String>> getImages(@PathVariable("id") String productId) {
        return ApiResponse.buildSuccessResponse("Fetched images from product successfully",
                productImageService.getUrlImages(productId));
    }

    @PutMapping("/thumbnail")
    public ApiResponse<Void> setThumbnail(@PathVariable("id") String productId,
                                          @Valid @RequestBody SetThumbnailRequest request) {
        productImageService.setThumbnail(productId, request.getFileName());
        return ApiResponse.buildSuccessResponse("Set thumbnail successfully", null);
    }
}
