package com.example.aiagent.config;

import com.example.aiagent.service.AIService;
import com.example.aiagent.service.OpenAIService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenAIConfig {

    @Bean
    public AIService aiService(Environment env) {
        String apiKey = env.getProperty("openai.api.key");
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = System.getenv("OPENAI_API_KEY");
        }
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                "Missing OpenAI API key. Set 'openai.api.key' property or the OPENAI_API_KEY environment variable.");
        }
        return new OpenAIService(apiKey);
    }
}