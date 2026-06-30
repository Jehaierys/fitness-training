package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeFacade {

    private final UserService userService;
    private final TraineeService traineeService;
    private final PasswordGenerator passwordGenerator;

    public Trainee signUp(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String address
    ) {
        UUID uuid = UUID.randomUUID();
        log.info("Signing up new trainee: {} {}, birth date: {}, address: {}, process's UUID: {}", firstName, lastName, birthDate, address, uuid);

        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String finalUsername = baseUsername;
        long suffix = 1;

        while (userService.existsByUsername(finalUsername)) {
            finalUsername = baseUsername + suffix;
            suffix++;
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(finalUsername)
                .password(passwordGenerator.generate())
                .isActive(true)
                .build();
        userService.create(user);

        Trainee trainee = Trainee.builder()
                .userId(user.getId())
                .birthDate(birthDate)
                .address(address)
                .build();

        log.info("Successfully created trainee: {} {}, birth date: {}, address: {}, userId: {} process's UUID: {}", firstName, lastName, birthDate, address, user.getId(), uuid);

        return traineeService.create(trainee);
    }
}