package com.example.Notification_service.service;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.example.Notification_service.dto.NotificationRequest;
import com.example.Notification_service.model.NotificationEntity;
import com.example.Notification_service.model.NotificationStatus;

class NotificationMapperTest {

    private final NotificationMapper mapper =
            new NotificationMapper();

    @Test
    void shouldMapRequestToEntity() {

        NotificationRequest request =
                new NotificationRequest();

        request.setNotificationId(UUID.randomUUID());
        request.setCorrelationId(UUID.randomUUID());
        request.setUserId("123");
        request.setChannel("EMAIL");
        request.setMessage("Hello World");
        request.setPriority("HIGH");
        request.setCreatedAt(LocalDateTime.now());
        request.setRetryCount(2);

        NotificationEntity entity =
                mapper.toEntity(
                        request,
                        NotificationStatus.PROCESSING
                );

        assertEquals(
                request.getNotificationId(),
                entity.getNotificationId()
        );

        assertEquals(
                request.getUserId(),
                entity.getUserId()
        );

        assertEquals(
                request.getChannel(),
                entity.getChannel()
        );

        assertEquals(
                request.getMessage(),
                entity.getMessage()
        );

        assertEquals(
                request.getPriority(),
                entity.getPriority()
        );

        assertEquals(
                NotificationStatus.PROCESSING,
                entity.getStatus()
        );

        assertEquals(
                2,
                entity.getRetryCount()
        );
    }

    @Test
    void shouldSetRetryCountToZeroWhenNull() {

        NotificationRequest request =
                new NotificationRequest();

        request.setRetryCount(null);

        NotificationEntity entity =
                mapper.toEntity(
                        request,
                        NotificationStatus.RECEIVED
                );

        assertEquals(
                0,
                entity.getRetryCount()
        );
    }
}