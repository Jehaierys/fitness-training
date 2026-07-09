package com.fitnesstraining.controller;

import com.fitnesstraining.dto.CoachSignUpRequest;
import com.fitnesstraining.dto.CoachSignUpResponse;
import com.fitnesstraining.openapi.CoachControllerApi;
import com.fitnesstraining.service.facade.CoachFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CoachController implements CoachControllerApi {

    private final CoachFacade coachFacade;


    public ResponseEntity<CoachSignUpResponse> signUp(CoachSignUpRequest request) {
        log.info("Received signup request for coach: {}", request.getFirstName());

        return new ResponseEntity<>(
                coachFacade.signUp(request),
                HttpStatus.CREATED
        );
    }
}
