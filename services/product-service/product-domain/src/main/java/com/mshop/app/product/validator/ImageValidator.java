package com.mshop.app.product.validator;

import com.mshop.app.common.core.exception.SystemCode;
import com.mshop.app.common.core.exception.SystemException;
import com.mshop.app.product.constant.FileConstant;
import com.mshop.app.product.exception.FileValidationException;
import com.mshop.app.product.exception.ProductServiceCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

@Slf4j
@Component
public class ImageValidator {

    private static final Set<String> ALLOWED_CONTENT_TYPES = FileConstant.ImageType
                    .getContentTypes();

    public void validate(MultipartFile file) {
        validateNotNull(file);
        validateNotEmpty(file);
        validateSize(file);
        validateContentType(file);
        validateDetectedType(file);
    }

    private void validateNotNull(MultipartFile file) {
        if (file == null) {
            throw new FileValidationException(ProductServiceCode.FILE_NULL,
                    null);
        }
    }

    private void validateNotEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileValidationException(ProductServiceCode.FILE_EMPTY,
                    file.getOriginalFilename());
        }
    }

    private void validateSize(MultipartFile file) {
        if (file.getSize() > FileConstant.MAX_FILE_SIZE) {
            throw new FileValidationException(ProductServiceCode.FILE_SIZE_EXCEEDED,
                    file.getOriginalFilename());
        }
    }

    private void validateContentType(MultipartFile file) {
        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new FileValidationException(ProductServiceCode.FILE_NOT_SUPPORTED,
                    file.getOriginalFilename());
        }
    }

    private void validateDetectedType(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            Tika tika = new Tika();
            String detectedType = tika.detect(inputStream);

            if (!ALLOWED_CONTENT_TYPES.contains(detectedType)) {
                throw new FileValidationException(ProductServiceCode.FILE_CORRUPTED_OR_INVALID_IMAGE,
                        fileName);
            }
        } catch (IOException e) {
            log.error("Failed to read the image file name={}", fileName);
            throw new SystemException(SystemCode.UNEXPECTED_ERROR, e);
        }
    }
}
