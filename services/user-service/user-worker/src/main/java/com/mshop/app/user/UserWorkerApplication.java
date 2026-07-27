package com.mshop.app.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.mshop.app.*")
@EnableScheduling
public class UserWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserWorkerApplication.class, args);
    }

}
