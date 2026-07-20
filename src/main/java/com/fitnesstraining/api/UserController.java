package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.UserControllerApi;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerApi {

    private final UserService service;


    public ResponseEntity<Void> setActive(Long id, Boolean active, User principal) {
        service.setActive(id, active, principal);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> setActive(String username, Boolean active, User principal) {
        service.setActive(username, active, principal);
        return ResponseEntity.ok().build();
    }
}
