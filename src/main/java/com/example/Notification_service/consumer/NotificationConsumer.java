package com.example.Notification_service.consumer;

import com.example.Notification_service.config.RabbitMQConfig;
import com.example.Notification_service.dto.NotificationRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private final RabbitTemplate rabbitTemplate;

    public NotificationConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.MAIN_QUEUE)
    public void consumeMessage(NotificationRequest request) {

        System.out.println("Processing notification: "
                + request.getNotificationId());

        try {

            // simulate failure
            if (request.getMessage().contains("fail")) {

                throw new RuntimeException("Simulated processing failure");
            }

            System.out.println(
                    request.getChannel()
                            + " notification sent successfully"
            );

        } catch (Exception ex) {

            request.setRetryCount(
                    request.getRetryCount() + 1
            );

            System.out.println(
                    "Retry attempt: "
                            + request.getRetryCount()
            );

            /*
             * max retry = 3
             */
            if (request.getRetryCount() >= 3) {

                System.out.println(
                        "Sending message to DLQ..."
                );

                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.DLQ_QUEUE,
                        request
                );

                return;
            }

            /*
             * send to retry queue
             */
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.RETRY_QUEUE,
                    request
            );
        }
    }
}