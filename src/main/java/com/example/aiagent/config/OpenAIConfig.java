package com.example.aiagent.config;

import com.example.aiagent.service.AIService;
import com.example.aiagent.service.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Value("${openai.api.key}")
    private String apiKey;

    @Bean
    public AIService aiService() {
        return new OpenAIService(apiKey);
    }
}