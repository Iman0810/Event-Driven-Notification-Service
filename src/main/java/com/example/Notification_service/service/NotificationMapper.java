package com.example.Notification_service.service;

import com.example.Notification_service.dto.NotificationRequest;
import com.example.Notification_service.model.NotificationEntity;
import com.example.Notification_service.model.NotificationStatus;
import org.springframework.stereotype.Service;

@Service
public class NotificationMapper {

    public NotificationEntity toEntity(
            NotificationRequest request,
            NotificationStatus status
    ) {

        NotificationEntity entity =
                new NotificationEntity();

        entity.setNotificationId(
                request.getNotificationId()
        );

        entity.setUserId(request.getUserId());

        entity.setChannel(request.getChannel());

        entity.setMessage(request.getMessage());

        entity.setPriority(request.getPriority());

        entity.setRetryCount(
                request.getRetryCount()
        );

        entity.setStatus(status);

        entity.setCreatedAt(
                request.getCreatedAt()
        );

        return entity;
    }
}