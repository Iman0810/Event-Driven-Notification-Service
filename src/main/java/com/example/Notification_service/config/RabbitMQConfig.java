package com.example.Notification_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String MAIN_QUEUE = "notification.queue";
    public static final String DLQ_QUEUE = "notification.dlq";

    @Bean
    public Queue notificationQueue() {

        return QueueBuilder.durable(MAIN_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", DLQ_QUEUE)
                .build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_QUEUE);
    }
}