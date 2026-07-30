package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.processor.JwtAuthenticator;
import com.fitnesstraining.logic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final JwtAuthenticator jwtAuthenticator;

    public ResponseCookie authenticate(UsernamePasswordAuthenticationRequest request) {
        return jwtAuthenticator.authenticate(request);
    }

    public void changePassword(User user, String newPassword) {
        userService.changePassword(user, newPassword);
    }

    public void setActive(Long id, Boolean active, User principal) {
        userService.setActive(id, active, principal);
    }

    public void setActive(String username, Boolean active, User principal) {
        userService.setActive(username, active, principal);
    }
}
