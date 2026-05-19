package com.example.Notification_service.processor;

import org.springframework.stereotype.Service;

import com.example.Notification_service.dto.NotificationRequest;

@Service
public class SMSProcessor implements NotificationProcessor{

    @Override
    public String getChannelType() {
        return "SMS";
    }

    @Override
    public void process(NotificationRequest request){

        System.out.println(
            "Sending SMS notification to the user: " + request.getUserId()
        );


        if(request.getMessage().contains("fail")){
            throw new RuntimeException("SMS sending failed");
        }

        System.out.println("SMS notification sent successfully");
    }
    
}

    