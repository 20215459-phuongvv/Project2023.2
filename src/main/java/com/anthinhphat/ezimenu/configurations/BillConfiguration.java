package com.anthinhphat.ezimenu.configurations;

import com.anthinhphat.ezimenu.services.BillService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BillConfiguration {
    @Bean
    public BillService billService(){
        return new BillService();
    }
}
