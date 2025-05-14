package com.example.aiagent.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * Test configuration to bootstrap a Spring Boot context for OpenAIConfig.
 */
@SpringBootConfiguration
@EnableAutoConfiguration
@Import(OpenAIConfig.class)
public class TestConfig {
}