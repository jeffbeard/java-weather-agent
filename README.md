# AI Agent

A command-line tool that uses the OpenAI API to generate text from a prompt.

## Getting Started

### Prerequisites

- Java 17
- Gradle 8.10

### Building

```bash
./gradlew build
```

### Configuration

Set the OpenAI API key as an environment variable:

```bash
export OPENAI_API_KEY="your-api-key"
```

Or in `src/main/resources/application.properties`:

```
openai.api.key=your-api-key
```

## Usage

```bash
java -jar build/libs/ai-agent-0.0.1-SNAPSHOT.jar Your prompt here
```

## Configuration

You can configure the following OpenAI API parameters:

- `openai.api.model` (default: `text-davinci-003`)
- `openai.api.temperature` (default: `0.7`)
- `openai.api.max_tokens` (default: `150`)

These can be set as environment variables or in `application.properties`.
