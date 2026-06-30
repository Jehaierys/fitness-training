package com.fitnesstraining.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {

    private PasswordGenerator passwordGenerator;
    private final String VALID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @BeforeEach
    void setUp() {
        passwordGenerator = new PasswordGenerator();
    }

    @Test
    void generate_passwordHasCorrectLength() {
        String password = passwordGenerator.generate();
        assertNotNull(password);
        assertEquals(10, password.length(), "Generated password should be 10 characters long");
    }

    @Test
    void generate_passwordContainsOnlyValidCharacters() {
        String password = passwordGenerator.generate();
        assertNotNull(password);
        for (char c : password.toCharArray()) {
            assertTrue(VALID_CHARACTERS.indexOf(c) != -1, "Password contains invalid character: " + c);
        }
    }

    @Test
    void generate_multiplePasswordsAreDifferent() {
        Set<String> generatedPasswords = new HashSet<>();
        int numberOfPasswordsToGenerate = 100;

        for (int i = 0; i < numberOfPasswordsToGenerate; i++) {
            generatedPasswords.add(passwordGenerator.generate());
        }

        assertEquals(numberOfPasswordsToGenerate, generatedPasswords.size(),
                "Generated passwords should be mostly unique (basic randomness check)");
    }

    @Test
    void generate_passwordIsNotEmpty() {
        String password = passwordGenerator.generate();
        assertFalse(password.isEmpty(), "Generated password should not be empty");
    }
}