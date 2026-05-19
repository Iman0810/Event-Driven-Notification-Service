package com.example.Notification_service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.Notification_service.config.RabbitMQConfig;
import com.example.Notification_service.dto.NotificationRequest;
import com.example.Notification_service.model.NotificationStatus;
import com.example.Notification_service.repository.NotificationRepository;
import com.example.Notification_service.service.NotificationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final NotificationRepository repository;
    private final NotificationMapper mapper;



    @RabbitListener(queues = RabbitMQConfig.MAIN_QUEUE)
    public void consumeMessage(NotificationRequest request) {

        repository.save(
                mapper.toEntity(
                        request,
                        NotificationStatus.PROCESSING
                )
        );


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

            /*
             * SAVE AS SUCCESS
             */
            repository.save(
                    mapper.toEntity(
                            request,
                            NotificationStatus.SUCCESS
                    )
            );

        } catch (RuntimeException ex) {

            if (request.getRetryCount() == null) {
                request.setRetryCount(0);
            }


            request.setRetryCount(
                    request.getRetryCount() + 1
            );

            /*
             * SAVE AS RETRYING
             */
            repository.save(
                    mapper.toEntity(
                            request,
                            NotificationStatus.RETRYING
                    )
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
                /*
                 * SAVE AS FAILED
                 */
                repository.save(
                        mapper.toEntity(
                                request,
                                NotificationStatus.FAILED
                        )
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