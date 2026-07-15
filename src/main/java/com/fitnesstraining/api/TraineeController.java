package com.fitnesstraining.api;

import com.fitnesstraining.domain.dto.abstraction.Activated;
import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.api.openapi.TraineeControllerApi;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeProfileRequest;
import com.fitnesstraining.domain.dto.trainee.response.GetTraineeResponse;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeProfileResponse;
import com.fitnesstraining.logic.abstraction.TraineeService;
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

    private final TraineeFacade traineeFacade;
    private final TraineeService traineeService;


    public ResponseEntity<UserSignUpResponse> signUp(TraineeSignUpRequest request) {
        log.info("Received signup request for trainee: {} {}", request.getFirstName(), request.getLastName());
        return new ResponseEntity<>(
                traineeFacade.signUp(request),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<UpdateTraineeProfileResponse> update(UpdateTraineeProfileRequest request) {
        log.info("Received update profile request for trainee: {}", request.getUsername());
        return ResponseEntity.ok(traineeFacade.updateProfile(request));
    }

    public ResponseEntity<HttpStatus> delete(String username) {
        log.info("Received delete request for trainee: {}", username);
        traineeService.deleteByUsername(username);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    public ResponseEntity<GetTraineeResponse> findByUsername(String username) {
        log.info("Received find by username request for trainee: {}", username);
        return ResponseEntity.ok((GetTraineeResponse) traineeFacade.findByUsername(username));
    }

    public ResponseEntity<HttpStatus> setActive(Activated request) {
        log.info("Received set active request for trainee: {}", request.getUsername());
        traineeFacade.setActive(request);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
