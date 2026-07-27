package com.mshop.app.kafka.consumer;

import com.mshop.app.kafka.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableKafka
public class KafkaConsumerConfig {

    private final KafkaProperties pros;

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> consumerConfigMap = new HashMap<>();
        consumerConfigMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, pros.getBootstrapServers());
        consumerConfigMap.put(ConsumerConfig.GROUP_ID_CONFIG, pros.getConsumer().getGroupId());
        consumerConfigMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfigMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerConfigMap.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        consumerConfigMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        consumerConfigMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, pros.getConsumer().getAutoOffsetReset());

        return new DefaultKafkaConsumerFactory<>(consumerConfigMap);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
