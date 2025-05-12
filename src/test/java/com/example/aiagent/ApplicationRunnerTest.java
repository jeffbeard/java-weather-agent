package com.example.aiagent;

import com.example.aiagent.service.AIService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationRunnerTest {

    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void noArgsShouldPrintUsageAndExit() {
        TestableApplication app = new TestableApplication();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));
        ExitTrappedException ex = assertThrows(ExitTrappedException.class, () -> app.run());
        assertEquals(1, ex.getStatus());
        String stderr = err.toString();
        assertTrue(stderr.contains("Usage: java -jar ai-agent.jar"));
    }

    @Test
    void withValidArgShouldCallServiceAndPrint() {
        TestableApplication app = new TestableApplication();
        AIService mockService = app.getMockService();
        when(mockService.getCompletion("hello")).thenReturn("world");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        app.run("hello");
        String stdout = out.toString();
        assertTrue(stdout.contains("Sending sanitized prompt: hello"));
        assertTrue(stdout.contains("AI Response: world"));
        verify(mockService, times(1)).getCompletion("hello");
    }

    // Custom exception to trap exit calls
    static class ExitTrappedException extends RuntimeException {
        private final int status;
        ExitTrappedException(int status) {
            super("Exit with status " + status);
            this.status = status;
        }
        int getStatus() {
            return status;
        }
    }

    // Subclass Application to override exit() and inject mock AIService
    static class TestableApplication extends Application {
        private final AIService mockService = Mockito.mock(AIService.class);

        TestableApplication() {
            setAiService(mockService);
        }

        AIService getMockService() {
            return mockService;
        }

        @Override
        protected void exit(int status) {
            throw new ExitTrappedException(status);
        }
    }
}