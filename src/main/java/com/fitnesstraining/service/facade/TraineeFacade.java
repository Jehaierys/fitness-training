package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class TraineeFacade {

    private static final SecureRandom random = new SecureRandom();
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final UserService userService;
    private final TraineeService traineeService;

    public Trainee signUp(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String address
    ) {
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
        user.setActive(true);User savedUser = userService.create(user);
        Trainee trainee = new Trainee();
        trainee.setUserId(savedUser.getId());
        trainee.setBirthDate(birthDate);
        trainee.setAddress(address);
        return traineeService.create(trainee);
    }
}