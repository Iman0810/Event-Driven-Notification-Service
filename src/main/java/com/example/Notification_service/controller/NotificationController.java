package com.example.Notification_service.controller;

import com.example.Notification_service.dto.NotificationRequest;
import com.example.Notification_service.producer.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.Notification_service.model.NotificationStatus;
import com.example.Notification_service.repository.NotificationRepository;
import com.example.Notification_service.service.NotificationMapper;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducer producer;
    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    @PostMapping
    public String sendNotification(
            @RequestBody NotificationRequest request
    ) {

        request.setNotificationId(UUID.randomUUID());

        request.setCreatedAt(LocalDateTime.now());

        repository.save(
                mapper.toEntity(request,NotificationStatus.RECEIVED));

        producer.sendNotification(request);

        return "Notification queued successfully";
    }
}