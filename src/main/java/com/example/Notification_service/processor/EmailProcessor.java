package com.example.Notification_service.processor;

import org.springframework.stereotype.Service;

import com.example.Notification_service.dto.NotificationRequest;

@Service
public class EmailProcessor implements NotificationProcessor{

    @Override
    public String getChannelType() {
        return "EMAIL";
    }
    @Override
    public void process(NotificationRequest request){

        System.out.println("Sending Email notification to the user: " + request.getUserId());


        if(request.getMessage().contains("fail")){
            throw new RuntimeException("Email sending failed");
        }

        System.out.println("Email notification sent successfully");
    }
    
}
