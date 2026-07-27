package com.mshop.app.kafka.producer;

import com.mshop.app.kafka.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Event<?> payload) {
        String key = payload.getObjectId();
        sendToKafka(topic, key, payload, kafkaTemplate);
    }

    private <T> void sendToKafka(String topic, String key, T payload, KafkaTemplate<String, T> kafkaTemplate) {
        kafkaTemplate.send(topic, key, payload)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Failed to send event to Kafka topic: {}, key = {}", topic, key, exception);
                    } else {
                        log.info("Successfully sent event to Kafka topic: {} with key: {}", topic, key);
                        log.debug("Kafka metadata -> Partition: [{}], Offset: [{}], Timestamp: [{}]",
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset(),
                                result.getRecordMetadata().timestamp());
                    }
                });
    }
}
