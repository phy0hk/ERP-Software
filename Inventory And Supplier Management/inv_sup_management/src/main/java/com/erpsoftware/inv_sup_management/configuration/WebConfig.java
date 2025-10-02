package com.erpsoftware.inv_sup_management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173","http://100.113.56.41:5173","http://100.78.57.57:5173")
                    .allowedMethods("GET","POST","PUT","DELETE")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}
