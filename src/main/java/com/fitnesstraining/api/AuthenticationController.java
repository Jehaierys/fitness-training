package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.AuthenticationControllerApi;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerApi {

    // todo: move to facade
    private final UserService userService;


    @PutMapping("/password")
    public void changePassword(User user, String newPassword) {
        userService.changePassword(user, newPassword);
    }
}
