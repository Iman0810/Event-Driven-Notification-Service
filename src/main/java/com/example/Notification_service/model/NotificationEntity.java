package com.example.Notification_service.model;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.Notification_service.dto.Priority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "notifications")
@Data
public class NotificationEntity {

    @Id
    private UUID notificationId;

    private String userId;

    private String channel;

    @Column(length = 1000)
    private String message;

    private Priority priority;

    private int retryCount;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime createdAt;
}