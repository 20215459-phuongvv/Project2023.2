package com.anthinhphat.ezimenu.configurations;

import com.anthinhphat.ezimenu.services.DishService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DishConfiguration {
    @Bean
    public DishService dishService(){
        return new DishService();
    }
}
