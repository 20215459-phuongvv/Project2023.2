package com.anthinhphat.ezimenu.configurations;

import com.anthinhphat.ezimenu.services.OrderItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderItemConfiguration {
    @Bean
    public OrderItemService orderItemService(){
        return new OrderItemService();
    }
}
