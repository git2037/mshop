package com.mshop.app.product.storage.minio;

import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MinIOSupplier<T> {
    T execute() throws IOException, MinioException,
            NoSuchAlgorithmException, InvalidKeyException;
}