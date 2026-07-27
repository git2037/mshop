package com.mshop.app.kafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaProperties {

    private String bootstrapServers;

    private Producer producer = new Producer();
    private Consumer consumer = new Consumer();

    @Getter @Setter
    public static class Producer {
        private String acks = "all";
        private boolean idempotence = true;
        private int retries = 3;
    }

    @Getter @Setter
    public static class Consumer {
        private String groupId;
        private String autoOffsetReset = "earliest";
    }
}
