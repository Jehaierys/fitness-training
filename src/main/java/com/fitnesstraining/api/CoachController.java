package com.fitnesstraining.api;

import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.api.openapi.CoachControllerApi;
import com.fitnesstraining.logic.facade.CoachFacade;
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


    public ResponseEntity<UserSignUpResponse> signUp(CoachSignUpRequest request) {
        log.info("Received signup request for coach: {}", request.getFirstName());

        return new ResponseEntity<>(
                coachFacade.signUp(request),
                HttpStatus.CREATED
        );
    }
}
