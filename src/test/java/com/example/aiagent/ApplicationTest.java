package com.example.aiagent;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    private static String sanitize(String input) {
        try {
            Method m = Application.class.getDeclaredMethod("sanitizePrompt", String.class);
            m.setAccessible(true);
            Application app = new Application();
            return (String) m.invoke(app, input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke sanitizePrompt", e);
        }
    }

    @Test
    void nullInputReturnsEmpty() {
        assertEquals("", sanitize(null));
    }

    @Test
    void emptyAndWhitespaceOnlyReturnsEmpty() {
        assertEquals("", sanitize(""));
        assertEquals("", sanitize("   "));
        assertEquals("", sanitize("\t\r\n"));
    }

    @Test
    void removesControlCharactersExceptCrLfAndTab() {
        String input = "a" + "\u0000" + "b" + "\u0008" + "\t" + "\n" + "\r" + "c";
        String expected = "ab" + "\t" + "\n" + "\r" + "c";
        assertEquals(expected, sanitize(input));
    }

    @Test
    void trimsLeadingAndTrailingWhitespace() {
        assertEquals("hello", sanitize("  hello  "));
        assertEquals("hello world", sanitize("\n\t  hello world  \t\n"));
    }

    @Test
    void doesNotTruncateWhenWithinMaxLength() {
        String input = "x".repeat(1000);
        assertEquals(1000, sanitize(input).length());
    }

    @Test
    void truncatesInputLongerThanMaxLength() {
        String input = "x".repeat(1100);
        String result = sanitize(input);
        assertEquals(1000, result.length());
        assertTrue(result.chars().allMatch(c -> c == 'x'));
    }
}