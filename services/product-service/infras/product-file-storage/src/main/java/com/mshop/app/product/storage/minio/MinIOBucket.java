package com.mshop.app.product.storage.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MinIOBucket {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void createBucketIfNotExists() {
        boolean isExist = MinIOExecutor.execute(() ->
                minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(bucketName).build())
        );

        if (!isExist) {

            log.info("Create bucket name ={}", bucketName);
            MinIOExecutor.execute(() ->
                    minioClient.makeBucket(MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build())
            );


            String policy = """
                    {
                      "Version": "2012-10-17",
                      "Statement": [
                        {
                          "Effect": "Allow",
                          "Principal": "*",
                          "Action": "s3:GetObject",
                          "Resource": "arn:aws:s3:::%s/*"
                        }
                      ]
                    }
                    """.formatted(bucketName);

            log.info("Set custom bucket name={} policy", bucketName);
            MinIOExecutor.execute(() ->
                    minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy).build())
            );
        } else
            log.info("bucket name = {} already exists", bucketName);
    }
}
