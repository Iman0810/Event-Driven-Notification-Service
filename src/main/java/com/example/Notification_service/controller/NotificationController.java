package com.example.Notification_service.controller;

import com.example.Notification_service.dto.NotificationRequest;
import com.example.Notification_service.producer.NotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationProducer producer;

    @PostMapping
    public String sendNotification(
            @RequestBody NotificationRequest request
    ) {

        request.setNotificationId(UUID.randomUUID());

        request.setCreatedAt(LocalDateTime.now());

        producer.sendNotification(request);

        return "Notification queued successfully";
    }
}