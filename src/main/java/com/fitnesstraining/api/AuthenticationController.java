package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.AuthenticationControllerApi;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.facade.CoachFacade;
import com.fitnesstraining.logic.facade.TraineeFacade;
import com.fitnesstraining.logic.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerApi {

    private final UserService userService;
    private final CoachFacade coachFacade;
    private final TraineeFacade traineeFacade;


    //todo: signUp or register?
    @PostMapping("/coaches")
    public ResponseEntity<UserSignUpResponse>  registerCoach(CoachSignUpRequest request) {
        log.info("Received signup request for coach: {}", request.getFirstName());
        return new ResponseEntity<>(
                coachFacade.signUp(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/trainees")
    public ResponseEntity<UserSignUpResponse> registerTrainee(TraineeSignUpRequest request) {
        log.info("Received signup request for trainee: {} {}", request.getFirstName(), request.getLastName());
        return new ResponseEntity<>(
                traineeFacade.signUp(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/users/password")
    public void changePassword(User user, String newPassword) {
        userService.changePassword(user, newPassword);
    }

//    @PostMapping("/login")
//    public void login(...) { ... }
//
//    @PostMapping("/logout")
//    public void logout(...) { ... }


//
//    @GetMapping("/me")
//    public UserDto me(...) { ... }
}
