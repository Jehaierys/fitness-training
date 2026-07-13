package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.SessionControllerApi;
import com.fitnesstraining.domain.dto.session.*;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.facade.SessionFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
            SessionSearchCriteria criteria,
            User user
    ) {
        // todo: extract somehow
        Long requestSenderId = user.getId();

        criteria.setRequestSenderId(requestSenderId);
        log.info("Received sessions request for user: {}", requestSenderId);
        return ResponseEntity.ok(facade.findSessionsByCriteria(criteria));
    }
}