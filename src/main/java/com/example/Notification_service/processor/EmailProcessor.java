package com.example.Notification_service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.Notification_service.dto.NotificationRequest;

@Service
public class EmailProcessor implements NotificationProcessor{

    private static final Logger logger = LoggerFactory.getLogger(EmailProcessor.class);

    @Override
    public String getChannelType() {
        return "EMAIL";
    }
    @Override
    public void process(NotificationRequest request){

        logger.info("[correlationId={}] Sending EMAIL notification to user {}",
        request.getCorrelationId(),
        request.getUserId());

        if(request.getMessage().contains("fail")){
            throw new RuntimeException("Email sending failed");
        }

        logger.info("[correlationId={}] EMAIL notification sent successfully", request.getCorrelationId(), request.getUserId());
    }
    
}
