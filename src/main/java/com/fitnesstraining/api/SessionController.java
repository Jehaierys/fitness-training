package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.SessionControllerApi;
import com.fitnesstraining.domain.dto.session.*;
import com.fitnesstraining.logic.facade.SessionFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SessionController implements SessionControllerApi {

    private final SessionFacade facade;


    public ResponseEntity<HttpStatus> create(SessionRegistrationRequest request) {
        facade.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // todo: check user gets only his own sessions
    public ResponseEntity<List<SessionDto>> sessions(
            @Valid @RequestBody SessionSearchCriteria criteria
    ) {
        // todo: extract somehow
        Long requestSenderId = 0L;

        log.info("Received sessions request for user: {}", requestSenderId);
        return ResponseEntity.ok(facade.findSessionsByCriteria(criteria));
    }
}