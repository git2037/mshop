package com.mshop.app.common.core.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.mshop.app.*")
@EntityScan(basePackages = "com.mshop.app.*")
@EnableJpaAuditing
public class JpaConfig {
}
