package com.example.Notification_service.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.Notification_service.config.RabbitMQConfig;
import com.example.Notification_service.dto.NotificationRequest;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final RabbitTemplate rabbitTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationProducer.class);

    public void sendNotification(NotificationRequest request) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.MAIN_QUEUE,
                request
        );

        logger.info("Notification queued: {}", request);
    }
}