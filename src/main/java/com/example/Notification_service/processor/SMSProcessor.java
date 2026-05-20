package com.example.Notification_service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.Notification_service.dto.NotificationRequest;
@Service
public class SMSProcessor implements NotificationProcessor{

    @Override
    public String getChannelType() {
        return "SMS";
    }

    private static final Logger logger =
        LoggerFactory.getLogger(SMSProcessor.class);

    @Override
    public void process(NotificationRequest request){

         logger.info("[correlationId={}] Sending SMS notification to user {}", request.getCorrelationId(), request.getUserId());


        if(request.getMessage().contains("fail")){
            throw new RuntimeException("SMS sending failed");
        }

        logger.info("[correlationId={}] SMS notification sent successfully", request.getCorrelationId(), request.getUserId());
    }
    
}

    