package com.example.Notification_service.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.Notification_service.dto.NotificationRequest;

@Service
public class PushProcessor implements NotificationProcessor{

    @Override
    public String getChannelType() {
        return "PUSH";
    }
    private static final Logger logger =
        LoggerFactory.getLogger(PushProcessor.class);

    @Override
    public void process(NotificationRequest request){

       logger.info("[correlationId={}] Sending PUSH notification to user {}",
        request.getCorrelationId(),
        request.getUserId());    

        if(request.getMessage().contains("fail")){
            throw new RuntimeException("Push notification sending failed");
        }

        logger.info("[correlationId={}] PUSH notification sent successfully", request.getCorrelationId(), request.getUserId());
    }
    
}
