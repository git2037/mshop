package com.mshop.app.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mshop.app.kafka.config.KafkaObjectMapper;
import com.mshop.app.kafka.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties pros;

    private Map<String, Object> baseConfig() {
        Map<String, Object> producerConfigs = new HashMap<>();
        producerConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, pros.getBootstrapServers());
        producerConfigs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        producerConfigs.put(ProducerConfig.ACKS_CONFIG, pros.getProducer().getAcks());
        producerConfigs.put(ProducerConfig.RETRIES_CONFIG, pros.getProducer().getRetries());

        producerConfigs.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producerConfigs.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);

        producerConfigs.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120000); // 2m
        producerConfigs.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000); // 30s

        return producerConfigs;
    }

    @Bean
    public ProducerFactory<String, Object> objectProducerFactory() {
        Map<String, Object> configs = baseConfig();
        ObjectMapper om = KafkaObjectMapper.getObjectMapper();
        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(),
                new JsonSerializer<>(om));
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(objectProducerFactory());
    }
}
