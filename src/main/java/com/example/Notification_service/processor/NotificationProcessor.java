package com.example.Notification_service.processor;

import com.example.Notification_service.dto.NotificationRequest;

public interface  NotificationProcessor {

    String getChannelType();
    

    void process(NotificationRequest request);
    
}
