package com.mshop.app.product.storage.minio;

import com.mshop.app.common.core.exception.SystemException;
import com.mshop.app.product.storage.exception.FileStorageCode;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MinIOExecutor {

    public static void execute(MinIOOperation operation) {
        execute(() -> {
            operation.execute();
            return null;
        });
    }

    public static <T> T execute(MinIOSupplier<T> supplier) {
        try {
            return supplier.execute();
        } catch (ErrorResponseException exception) {
            log.error("MinIO return an error response:{}", exception.errorResponse(), exception);
            throw new SystemException(FileStorageCode.MINIO_ERROR_RESPONSE);
        } catch (IOException | ServerException exception) {
            log.error("Failed to communicate with MinIO", exception);
            throw new SystemException(FileStorageCode.MINIO_COMMUNICATE_ERROR);
        } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException exception) {
            log.error("MinIO SDK errors", exception);
            throw new SystemException(FileStorageCode.MINIO_SDK_ERROR);
        }
    }
}
