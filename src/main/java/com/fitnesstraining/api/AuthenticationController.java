package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.AuthenticationControllerApi;
import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.domain.dto.response.JwtAuthenticationResponse;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.facade.UserFacade;
import com.fitnesstraining.logic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerApi {

    private final UserFacade facade;

    public void changePassword(User user, String newPassword) {
        facade.changePassword(user, newPassword);
    }

    public ResponseEntity<JwtAuthenticationResponse> login(
            UsernamePasswordAuthenticationRequest dto,
            String ip
    ) {
        return ResponseEntity.ok(facade.authenticate(dto));
    }

    public ResponseEntity<Void> setActive(Long id, Boolean active, User principal) {
        facade.setActive(id, active, principal);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> setActive(String username, Boolean active, User principal) {
        facade.setActive(username, active, principal);
        return ResponseEntity.ok().build();
    }
}
