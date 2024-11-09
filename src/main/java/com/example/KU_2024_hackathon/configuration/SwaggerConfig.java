package com.example.KU_2024_hackathon.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig
{
    // Swagger 기본 설정
    @Bean
    public OpenAPI openAPI()
    {
        Info info = new Info()
                .title("2024 InThon 해커톤 API");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
