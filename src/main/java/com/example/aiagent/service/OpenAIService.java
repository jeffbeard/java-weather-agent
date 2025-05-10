package com.example.aiagent.service;

public class OpenAIService implements AIService {

    private final String apiKey;

    public OpenAIService(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getCompletion(String prompt) {
        // TODO: Integrate with OpenAI API using apiKey
        // This is a stub implementation.
        return "Stub response for prompt: " + prompt;
    }
}