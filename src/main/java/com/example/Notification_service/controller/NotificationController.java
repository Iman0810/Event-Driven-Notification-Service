package com.example.Notification_service.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Notification_service.dto.NotificationRequest;
import com.example.Notification_service.model.NotificationStatus;
import com.example.Notification_service.producer.NotificationProducer;
import com.example.Notification_service.repository.NotificationRepository;
import com.example.Notification_service.service.NotificationMapper;

import lombok.RequiredArgsConstructor;

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
        
        request.setCorrelationId(UUID.randomUUID());

        request.setCreatedAt(LocalDateTime.now());

        repository.save(
                mapper.toEntity(request,NotificationStatus.RECEIVED));

        producer.sendNotification(request);

        return "Notification queued successfully";
    }
}