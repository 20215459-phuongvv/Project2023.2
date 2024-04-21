package com.anthinhphat.ezimenu.configurations;

import com.anthinhphat.ezimenu.services.MenuService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuConfiguration {
    @Bean
    public MenuService menuService(){
        return new MenuService();
    }
}
