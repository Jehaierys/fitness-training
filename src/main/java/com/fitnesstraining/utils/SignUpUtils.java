package com.fitnesstraining.utils;

import com.fitnesstraining.logic.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SignUpUtils {

    private final SecureRandom random = new SecureRandom();
    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    public String generatePassword() {
        return random.ints(10, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    public String generateUsername(String firstName, String lastName, UserService userService) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String username = baseUsername;
        long suffix = 1;

        while (userService.existsByUsername(username)) {
            username = baseUsername + suffix;
            suffix++;
        }

        return username;
    }
}
