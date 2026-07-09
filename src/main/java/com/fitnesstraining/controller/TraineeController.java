package com.fitnesstraining.controller;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.dto.TraineeSignUpResponse;
import com.fitnesstraining.openapi.TraineeControllerApi;
import com.fitnesstraining.service.facade.TraineeFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TraineeController implements TraineeControllerApi {

    private final TraineeFacade traineeFacade;


    public ResponseEntity<TraineeSignUpResponse> signUp(@Valid @RequestBody TraineeSignUpRequest request) {
        log.info("Received signup request for trainee: {}", request.getFirstName());

        return new ResponseEntity<>(
                traineeFacade.signUp(request),
                HttpStatus.CREATED
        );
    }
}
