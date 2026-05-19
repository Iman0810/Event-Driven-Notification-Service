package com.example.Notification_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Notification_service.processor.NotificationProcessor;

@Service
public class NotificationProcessorFactory {

    private final List<NotificationProcessor> processors;

    public NotificationProcessorFactory(
            List<NotificationProcessor> processors
    ) {

        this.processors = processors;
    }

    public NotificationProcessor getProcessor(
            String channel
    ) {

        return processors.stream()

                .filter(processor ->
                        processor.getChannelType()
                                .equalsIgnoreCase(channel)
                )

                .findFirst()

                .orElseThrow(() ->
                        new RuntimeException(
                                "Unsupported notification channel"
                        )
                );
    }
}