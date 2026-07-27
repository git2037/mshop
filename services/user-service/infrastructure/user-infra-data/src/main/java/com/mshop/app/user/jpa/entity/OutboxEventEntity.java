package com.mshop.app.user.jpa.entity;

import com.mshop.app.common.core.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "outbox_events", schema = "user_service")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OutboxEventEntity extends BaseEntity {
    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;

    @Column(name = "object_id", length = 36)
    private String objectId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "json")
    private Map<String, Object> payload;

    @Column(name = "sent_at")
    private Instant sentAt;

    @ColumnDefault("0")
    @Column(name = "retry_count", nullable = false)
    private Integer retryCount;
}