package com.example.Notification_service.processor;

import org.springframework.stereotype.Service;

import com.example.Notification_service.dto.NotificationRequest;

@Service
public class PushProcessor implements NotificationProcessor{

    @Override
    public String getChannelType() {
        return "PUSH";
    }

    @Override
    public void process(NotificationRequest request){

        System.out.println(
            "Sending Push notification to the user: " 
            + request.getUserId()
        );

        if(request.getMessage().contains("fail")){
            throw new RuntimeException("Push notification sending failed");
        }

        System.out.println("Push notification sent successfully");
    }
    
}
