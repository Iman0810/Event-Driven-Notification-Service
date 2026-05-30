package com.example.Notification_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private UUID notificationId;
    
    private UUID correlationId;

    private String userId;

    private String channel;

    private String message;

    private Priority priority;

    private LocalDateTime createdAt;

    private Integer retryCount =0;
}