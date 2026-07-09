package com.fitnesstraining.controller;

import com.fitnesstraining.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.openapi.TraineeControllerApi;
import com.fitnesstraining.service.facade.TraineeFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TraineeController implements TraineeControllerApi {

    private final TraineeFacade traineeFacade;


    public ResponseEntity<UserSignUpResponse> signUp(TraineeSignUpRequest request) {
        log.info("Received signup request for trainee: {}", request.getFirstName());

        return new ResponseEntity<>(
                traineeFacade.signUp(request),
                HttpStatus.CREATED
        );
    }
}
