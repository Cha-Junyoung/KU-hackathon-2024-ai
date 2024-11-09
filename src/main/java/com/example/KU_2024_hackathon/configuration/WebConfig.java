package com.example.KU_2024_hackathon.configuration;

// CORS 설정

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedOriginPatterns("*")   // CORS 허용 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // CORS 허용 메서드
    }
}
