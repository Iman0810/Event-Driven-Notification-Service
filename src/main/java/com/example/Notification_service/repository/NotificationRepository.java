package com.example.Notification_service.repository;

import com.example.Notification_service.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository
        extends JpaRepository<NotificationEntity, UUID> {
}