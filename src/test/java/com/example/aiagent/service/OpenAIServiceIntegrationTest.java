package com.example.aiagent.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for OpenAIService HTTP interactions, using WireMock to stub the OpenAI API.
 */
class OpenAIServiceIntegrationTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startServer() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void getCompletionReturnsTrimmedText() {
        String prompt = "Hello";
        String responseText = " world ";
        String jsonResponse = "{\n" +
                "  \"choices\": [\n" +
                "    {\"text\": \"" + responseText + "\"}\n" +
                "  ]\n" +
                "}";
        stubFor(post(urlEqualTo("/v1/completions"))
                .withHeader(HttpHeaders.AUTHORIZATION, com.github.tomakehurst.wiremock.client.WireMock.equalTo("Bearer test-api-key"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(jsonResponse)));

        WebClient customClient = WebClient.builder()
                .baseUrl(wireMockServer.baseUrl() + "/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer test-api-key")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        OpenAIService service = new OpenAIService(customClient);

        String result = service.getCompletion(prompt);
        assertEquals("world", result);
    }

    @Test
    void getCompletionHandlesErrorResponse() {
        stubFor(post(urlEqualTo("/v1/completions"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"error\": \"Bad request\"}")));

        WebClient customClient = WebClient.builder()
                .baseUrl(wireMockServer.baseUrl() + "/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer test-api-key")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        OpenAIService service = new OpenAIService(customClient);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getCompletion("Hello"));
        assertTrue(ex.getMessage().contains("OpenAI API error"));
    }
}