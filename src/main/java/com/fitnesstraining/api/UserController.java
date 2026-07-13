package com.fitnesstraining.api;


import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    public void changePassword(User user, String newPassword) {
        service.changePassword(user, newPassword);
    }
}
