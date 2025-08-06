package com.example.aiagent;

import com.example.aiagent.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private AIService aiService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        try {
            app.run(args);
        } catch (Exception ex) {
            Throwable cause = ex;
            while (cause != null) {
                if (cause instanceof IllegalStateException
                    && cause.getMessage().contains("Missing OpenAI API key")) {
                    System.out.println("Error: " + cause.getMessage());
                    System.exit(1);
                }
                cause = cause.getCause();
            }
            throw ex;
        }
    }

    @Override
    public void run(String... args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar ai-agent.jar <prompt>");
            exit(1);
            return;
        }
        String prompt = String.join(" ", args);
        String sanitizedPrompt = sanitizePrompt(prompt);
        System.out.println("Sending sanitized prompt: " + sanitizedPrompt);

        String response = aiService.getCompletion(sanitizedPrompt);
        System.out.println("AI Response: " + response);
    }

    private String sanitizePrompt(String prompt) {
        if (prompt == null) {
            return "";
        }
        String sanitized = prompt.replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]", "");
        sanitized = sanitized.trim();
        if (sanitized.length() > 1000) {
            sanitized = sanitized.substring(0, 1000);
        }
        return sanitized;
    }
    /**
     * Exit the application with the given status code. Protected for testing.
     */
    protected void exit(int status) {
        System.exit(status);
    }

    /**
     * Setter for AIService, for testing purposes.
     */
    void setAiService(com.example.aiagent.service.AIService aiService) {
        this.aiService = aiService;
    }
}