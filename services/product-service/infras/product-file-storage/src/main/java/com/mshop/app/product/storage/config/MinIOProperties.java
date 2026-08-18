package com.mshop.app.product.storage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class MinIOProperties {
    private String accessKey;
    private String secretKey;
    private String url;
}
