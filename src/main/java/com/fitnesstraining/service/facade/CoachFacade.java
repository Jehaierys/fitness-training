package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CoachFacade {

    private final UserService userService;
    private final CoachService coachService;

    private static final SecureRandom random = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public Coach signUp(String firstName, String lastName, String specialization) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String finalUsername = baseUsername;
        long suffix = 1;

        while (userService.existsByUsername(finalUsername)) {
            finalUsername = baseUsername + suffix;
            suffix++;
        }

        String generatedPassword = random.ints(10, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(finalUsername);
        user.setPassword(generatedPassword);
        user.setActive(true);
        User savedUser = userService.create(user);

        Coach coach = new Coach();
        coach.setUserId(savedUser.getId());
        coach.setSpecialization(specialization);

        return coachService.create(coach);
    }
}