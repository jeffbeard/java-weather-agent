<!--
This file lists pending tasks and features for the ai-agent project.
-->
# TODO

- [x] Integrate `OpenAIService.getCompletion` with the real OpenAI HTTP API using the configured API key (e.g., via Spring WebClient), parse the JSON response, and return the generated text.
- [x] Write unit/integration tests for `OpenAIService` HTTP integration (mock WebClient or use WireMock).
- [x] Add a Spring Boot Test or use `ApplicationContextRunner` to verify that when `openai.api.key` is set, the `AIService` bean is instantiated as `OpenAIService`.
- [ ] Update CLI prompt handling to accept multiple command-line arguments (join `args[]` into a single prompt) and add tests covering multi-word prompts.
- [ ] Implement clear error handling for missing or invalid API key: detect absence or invalid format early, print a descriptive "missing OPENAI_API_KEY" or "invalid API key" message, and add tests for these scenarios.
- [ ] Expose OpenAI API parameters (`model`, `temperature`, `max_tokens`) via command-line flags or Spring properties with sensible defaults; update tests and documentation accordingly.
- [ ] Add support for a `--help` / `-h` flag (or a dedicated help subcommand) to print usage and flag descriptions.
- [ ] Update documentation/README:
    - [ ] Describe how to set the OpenAI API key (environment variable or `application.properties`).
    - [ ] Explain configuration of model, temperature, and max_tokens.
    - [ ] Provide examples of running the application with multi-word prompts.
- [ ] (Future) Plan for advanced features such as Chat-style endpoints or streaming responses for real-time output.