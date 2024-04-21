package com.anthinhphat.ezimenu.configurations;

import com.anthinhphat.ezimenu.services.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {
    @Bean
    public NotificationService notificationService(){
        return new NotificationService();
    }
}
