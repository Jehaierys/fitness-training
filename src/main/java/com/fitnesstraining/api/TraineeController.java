package com.fitnesstraining.api;

import com.fitnesstraining.domain.dto.abstraction.Activated;
import com.fitnesstraining.api.openapi.TraineeControllerApi;
import com.fitnesstraining.domain.dto.abstraction.RegisterUserResponse;
import com.fitnesstraining.domain.dto.trainee.request.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.response.GetTraineeResponse;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeResponse;
import com.fitnesstraining.logic.facade.TraineeFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TraineeController implements TraineeControllerApi {

    private final TraineeFacade facade;


    public ResponseEntity<RegisterUserResponse> register(RegisterTraineeRequest request) {
        log.info("Received signup request for trainee: {} {}", request.getFirstName(), request.getLastName());
        return new ResponseEntity<>(
                facade.register(request),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<UpdateTraineeResponse> update(UpdateTraineeRequest request) {
        log.info("Received update profile request for trainee: {}", request.getUsername());
        return ResponseEntity.ok(facade.update(request));
    }

    public ResponseEntity<HttpStatus> delete(String username) {
        log.info("Received delete request for trainee: {}", username);
        facade.deleteByUsername(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<GetTraineeResponse> findByUsername(String username) {
        log.info("Received find by username request for trainee: {}", username);
        return ResponseEntity.ok(facade.findByUsername(username));
    }

    public ResponseEntity<HttpStatus> setActive(Activated request) {
        log.info("Received set active request for trainee: {}", request.getUsername());
        facade.setActive(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
