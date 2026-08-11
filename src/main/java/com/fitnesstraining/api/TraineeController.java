package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.TraineeControllerApi;
import com.fitnesstraining.domain.dto.response.RegisterUserResponse;
import com.fitnesstraining.domain.dto.request.trainee.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.request.trainee.UpdateTraineeRequest;
import com.fitnesstraining.domain.dto.response.trainee.GetTraineeResponse;
import com.fitnesstraining.domain.dto.response.trainee.UpdateTraineeResponse;
import com.fitnesstraining.service.TraineeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class TraineeController implements TraineeControllerApi {

    private final TraineeService service;


    public ResponseEntity<RegisterUserResponse> register(RegisterTraineeRequest request) {
        log.info("Received signup request for trainee: {} {}", request.getFirstName(), request.getLastName());
        return new ResponseEntity<>(
                // todo: returns null userId
                service.register(request),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<UpdateTraineeResponse> update(UpdateTraineeRequest request) {
        log.info("Received update profile request for trainee: {}", request.getUsername());
        return ResponseEntity.ok(service.update(request));
    }

    public ResponseEntity<HttpStatus> delete(String username) {
        log.info("Received delete request for trainee: {}", username);
        service.deleteByUsername(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<GetTraineeResponse> findByUsername(String username) {
        log.info("Received find by username request for trainee: {}", username);
        return ResponseEntity.ok(service.findByUsername(username));
    }
}
