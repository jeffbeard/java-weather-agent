package com.example.aiagent.config;

import com.example.aiagent.config.TestConfig;
import com.example.aiagent.service.AIService;
import com.example.aiagent.service.OpenAIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test to verify that when the OpenAI API key property is set,
 * the AIService bean is instantiated as OpenAIService.
 */
@SpringBootTest(classes = TestConfig.class,
                  properties = "openai.api.key=test-key")
class OpenAIConfigIntegrationTest {

    @Autowired
    private AIService aiService;

    @Test
    void whenApiKeyPresent_thenAIServiceIsOpenAIService() {
        assertNotNull(aiService, "AIService bean should be instantiated");
        assertTrue(aiService instanceof OpenAIService, "AIService should be an instance of OpenAIService");
    }
    
}