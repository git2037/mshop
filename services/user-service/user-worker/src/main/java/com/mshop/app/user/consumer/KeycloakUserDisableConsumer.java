package com.mshop.app.user.consumer;

import com.mshop.app.kafka.consumer.KafkaEventConsumer;
import com.mshop.app.kafka.event.Event;
import com.mshop.app.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakUserDisableConsumer {

    private final KafkaEventConsumer kafkaEventConsumer;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000, multiplier = 2.0),
            exclude = {UserNotFoundException.class}
    )
    @KafkaListener(topics = "${kafka.user-topic}", groupId = "${kafka.consumer.group-id}")
    public void consume(Event<?> event) {
        kafkaEventConsumer.consume(event);
    }

    @DltHandler
    public void handleDltEvent(Event<?> event) {
        log.error("Event '{}' went to DLT", event);
    }
}
