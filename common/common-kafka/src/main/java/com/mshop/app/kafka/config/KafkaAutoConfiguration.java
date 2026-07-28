package com.mshop.app.kafka.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan(basePackageClasses = KafkaProperties.class)
public class KafkaAutoConfiguration {
}
