package com.example.Notification_service.consumer;

import com.example.Notification_service.config.RabbitMQConfig;
import com.example.Notification_service.dto.NotificationRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DLQConsumer {

    @RabbitListener(queues = RabbitMQConfig.DLQ_QUEUE)
    public void receiveFailedMessages(
            NotificationRequest request
    ) {

        System.out.println(
                "DLQ RECEIVED FAILED NOTIFICATION"
        );

        System.out.println(request);
    }
}