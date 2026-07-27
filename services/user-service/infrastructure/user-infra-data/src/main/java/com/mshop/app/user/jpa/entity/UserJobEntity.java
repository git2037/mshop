package com.mshop.app.user.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_job", schema = "user_service")
public class UserJobEntity {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_processed_time", nullable = false)
    private Long lastProcessedTime;
}