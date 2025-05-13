package com.example.aiagent.service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OpenAIService implements AIService {

    private final WebClient webClient;

    /**
     * Package-private constructor for injecting a custom WebClient (e.g., for testing).
     */
    OpenAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public OpenAIService(String apiKey) {
        this(WebClient.builder()
            .baseUrl("https://api.openai.com/v1")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build());
    }

    @Override
    public String getCompletion(String prompt) {
        try {
            CompletionRequest request = new CompletionRequest("text-davinci-003", prompt, 0.7, 150);
            CompletionResponse response = webClient.post()
                .uri("/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CompletionResponse.class)
                .block();
            if (response == null || response.choices == null || response.choices.isEmpty()) {
                throw new RuntimeException("No completion returned from OpenAI API");
            }
            String text = response.choices.get(0).text;
            return text != null ? text.trim() : "";
        } catch (WebClientResponseException e) {
            throw new RuntimeException(
                "OpenAI API error: " + e.getRawStatusCode() + " " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get completion from OpenAI API", e);
        }
    }

    // Internal classes for request/response mapping
    private static class CompletionRequest {
        @JsonProperty("model")
        String model;
        @JsonProperty("prompt")
        String prompt;
        @JsonProperty("temperature")
        double temperature;
        @JsonProperty("max_tokens")
        int maxTokens;

        CompletionRequest(String model, String prompt, double temperature, int maxTokens) {
            this.model = model;
            this.prompt = prompt;
            this.temperature = temperature;
            this.maxTokens = maxTokens;
        }
    }

    private static class CompletionResponse {
        @JsonProperty("choices")
        List<Choice> choices;
    }

    private static class Choice {
        @JsonProperty("text")
        String text;
    }
}