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
        app.run(args);
    }

    @Override
    public void run(String... args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar ai-agent.jar \"<prompt>\"");
            System.exit(1);
        }
        String prompt = args[0];
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
}