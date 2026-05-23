package com.example.Notification_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String MAIN_QUEUE = "notification.queue";

    public static final String RETRY_QUEUE = "notification.retry.queue";

    public static final String DLQ_QUEUE = "notification.dlq";

    //Main queue
    @Bean
    public Queue mainQueue() {

        return QueueBuilder.durable(MAIN_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", RETRY_QUEUE)
                .build();
    }

    //retry queue
    @Bean
    public Queue retryQueue() {

        return QueueBuilder.durable(RETRY_QUEUE)

                // wait 5 seconds
                .withArgument("x-message-ttl", 5000)

                // send back to main queue
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", MAIN_QUEUE)

                .build();
    }

    // dead letter queue
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ_QUEUE).build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}