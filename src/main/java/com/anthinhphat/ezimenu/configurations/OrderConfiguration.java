package com.anthinhphat.ezimenu.configurations;

import com.anthinhphat.ezimenu.services.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {
    @Bean
    public OrderService orderService(){
        return new OrderService();
    }
}
