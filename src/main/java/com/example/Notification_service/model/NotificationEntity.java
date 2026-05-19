package com.example.Notification_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

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

    private String priority;

    private int retryCount;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime createdAt;
}