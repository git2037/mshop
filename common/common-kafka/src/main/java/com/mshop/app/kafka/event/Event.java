package com.mshop.app.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class Event<T> {
    private String eventId;
    private String eventType;
    private String objectId;
    private Instant timestamp;
    private T payload;

    public static <T> Event<T> create(String type, String objectId, T payload) {
        return Event.<T>builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(type)
                .objectId(objectId)
                .timestamp(Instant.now())
                .payload(payload).build();
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType='" + eventType + '\'' +
                ", objectId='" + objectId + '\'' +
                ", payload=" + payload +
                '}';
    }
}
