package com.mshop.app.product.storage.repository;

import com.mshop.app.product.constant.ProductServiceConstant;
import com.mshop.app.product.repository.FileStorageRepository;
import com.mshop.app.product.storage.minio.MinIOExecutor;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MinIORepositoryImpl implements FileStorageRepository {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Value("${minio.product-image-prefix}")
    private String productImagePrefix;

    @Override
    public String upload(String productId, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName == null
                ? "png" : FilenameUtils.getExtension(originalFileName);

        String fileName = productImagePrefix + ProductServiceConstant.FORWARD_SLASH
                + productId + ProductServiceConstant.FORWARD_SLASH +
                UUID.randomUUID() + "." + extension;

        MinIOExecutor.execute(() ->
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName)
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()));

        return fileName;
    }

    @Override
    public void removeAll(List<String> uploadedFileNames) {

        List<DeleteObject> objects = uploadedFileNames.stream()
                .map(DeleteObject::new)
                .toList();

        Iterable<Result<DeleteError>> results =
                minioClient.removeObjects(
                        RemoveObjectsArgs.builder()
                                .bucket(bucketName)
                                .objects(objects)
                                .build());

        for (Result<DeleteError> result : results) {
            DeleteError error = MinIOExecutor.execute(result::get);
            log.error("Error in deleting object name={}, message={}", error.objectName(), error.message());
        }
    }
}
