package com.example.Notification_service.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.Notification_service.config.RabbitMQConfig;
import com.example.Notification_service.dto.NotificationRequest;
import com.example.Notification_service.model.NotificationStatus;
import com.example.Notification_service.repository.NotificationRepository;
import com.example.Notification_service.service.NotificationMapper;
import com.example.Notification_service.service.NotificationProcessorFactory;
import com.example.Notification_service.util.LogUtil;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final NotificationRepository repository;
    private final NotificationMapper mapper;
    private final NotificationProcessorFactory processorFactory;

    private static final Logger logger =
        LoggerFactory.getLogger(NotificationConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.MAIN_QUEUE)
    public void consumeMessage(NotificationRequest request) {

        repository.save(
                mapper.toEntity(
                        request,
                        NotificationStatus.PROCESSING
                )
        );

        LogUtil.log(
                logger,
                request.getCorrelationId().toString(),
                "Processing notification: " + request.getNotificationId()
        );

        try {

            processorFactory.getProcessor(request.getChannel())
                        .process(request);

           
            repository.save(
                    mapper.toEntity(
                            request,
                            NotificationStatus.SUCCESS
                    )
            );

        } catch (RuntimeException ex) {

                logger.error(
    "[correlationId={}] Notification processing failed: {}",
    request.getCorrelationId(),
    ex.getMessage()
);

            if (request.getRetryCount() == null) {
                request.setRetryCount(0);
            }


            request.setRetryCount(
                    request.getRetryCount() + 1
            );

         
            repository.save(
                    mapper.toEntity(
                            request,
                            NotificationStatus.RETRYING
                    )
            );


                logger.warn(
    "[correlationId={}] Retry count for notification {}: {}",
    request.getCorrelationId(),
    request.getNotificationId(),
    request.getRetryCount()
);
          
            if (request.getRetryCount() >= 3) {

                logger.error(
    "[correlationId={}] Max retry attempts reached for notification: {}",
    request.getCorrelationId(),
    request.getNotificationId()
);
                
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

        
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.RETRY_QUEUE,
                    request
            );
        }
    }
}