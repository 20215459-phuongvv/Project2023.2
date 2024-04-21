package com.anthinhphat.ezimenu.configurations;

import com.anthinhphat.ezimenu.services.TableService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TableConfiguration {
    @Bean
    public TableService tableService(){
        return new TableService();
    }
}
