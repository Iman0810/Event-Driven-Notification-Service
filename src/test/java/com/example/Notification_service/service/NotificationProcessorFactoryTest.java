package com.example.Notification_service.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Notification_service.processor.EmailProcessor;
import com.example.Notification_service.processor.NotificationProcessor;
import com.example.Notification_service.processor.PushProcessor;
import com.example.Notification_service.processor.SMSProcessor;

class NotificationProcessorFactoryTest {

    private NotificationProcessorFactory factory;

    @BeforeEach
    void setUp() {

        factory = new NotificationProcessorFactory(
                List.of(
                        new EmailProcessor(),
                        new SMSProcessor(),
                        new PushProcessor()
                )
        );
    }

    @Test
    void shouldReturnEmailProcessor() {

        NotificationProcessor processor =
                factory.getProcessor("EMAIL");

        assertTrue(processor instanceof EmailProcessor);
    }

    @Test
    void shouldReturnSMSProcessor() {

        NotificationProcessor processor =
                factory.getProcessor("SMS");

        assertTrue(processor instanceof SMSProcessor);
    }

    @Test
    void shouldReturnPushProcessor() {

        NotificationProcessor processor =
                factory.getProcessor("PUSH");

        assertTrue(processor instanceof PushProcessor);
    }
}