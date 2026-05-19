package com.example.Notification_service.util;

import org.slf4j.Logger;

public class LogUtil {

    public static void log(Logger logger,
        String correlationId,
        String message
    ) {
        logger.info("[correlationId={}] {}",
                correlationId,
                message);
    }
    
}
