package com.example.Notification_service.producer;

import com.example.Notification_service.config.RabbitMQConfig;
import com.example.Notification_service.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendNotification(NotificationRequest request) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.MAIN_QUEUE,
                request
        );

        System.out.println("Notification queued: " + request);
    }
}