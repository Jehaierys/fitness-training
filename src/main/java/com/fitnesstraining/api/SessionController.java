package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.SessionControllerApi;
import com.fitnesstraining.domain.dto.session.SessionRegistrationRequest;
import com.fitnesstraining.logic.facade.SessionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SessionController implements SessionControllerApi {

    private final SessionFacade facade;

    public ResponseEntity<HttpStatus> create(SessionRegistrationRequest request) {
        facade.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
