package com.example.Notification_service.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.Notification_service.config.RabbitMQConfig;
import com.example.Notification_service.dto.NotificationRequest;

@Service
public class DLQConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DLQConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.DLQ_QUEUE)
    public void receiveFailedMessages(
            NotificationRequest request
    ) {

        logger.info("DLQ RECEIVED FAILED NOTIFICATION");

        logger.info("Failed notification: {}", request);
    }
}