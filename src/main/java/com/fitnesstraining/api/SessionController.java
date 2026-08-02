package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.SessionControllerApi;
import com.fitnesstraining.domain.dto.request.session.SessionRegistrationRequest;
import com.fitnesstraining.domain.dto.request.session.SessionSearchCriteria;
import com.fitnesstraining.domain.dto.response.SessionDto;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.service.SessionService;
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

    private final SessionService service;


    public ResponseEntity<HttpStatus> create(SessionRegistrationRequest request) {
        service.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<List<SessionDto>> sessions(
            SessionSearchCriteria criteria,
            User user
    ) {
        final Long requestSenderId = user.getId();

        criteria.setRequestSenderId(requestSenderId);
        log.info("Received sessions request for user: {}", requestSenderId);
        return ResponseEntity.ok(service.findSessionsByCriteria(criteria));
    }
}