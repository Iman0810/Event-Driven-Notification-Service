package com.example.Notification_service.consumer;

import com.example.Notification_service.config.RabbitMQConfig;

import com.example.Notification_service.dto.NotificationRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = RabbitMQConfig.MAIN_QUEUE)
    public void consumeMessage(NotificationRequest request) {

        System.out.println("Received notification: " + request);

        if (request.getMessage().contains("fail")) {

            System.out.println("Notification processing failed!");

            throw new RuntimeException("Simulated failure");
        }

        System.out.println(
                request.getChannel() +
                        " notification processed successfully"
        );
    }
}