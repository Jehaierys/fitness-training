package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.CoachControllerApi;
import com.fitnesstraining.domain.dto.response.RegisterUserResponse;
import com.fitnesstraining.domain.dto.request.coach.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.request.coach.UpdateCoachRequest;
import com.fitnesstraining.domain.dto.response.coach.CoachDto;
import com.fitnesstraining.domain.dto.response.coach.GetCoachResponse;
import com.fitnesstraining.domain.dto.response.coach.UpdateCoachResponse;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.service.CoachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CoachController implements CoachControllerApi {

    private final CoachService service;


    public ResponseEntity<RegisterUserResponse> register(RegisterCoachRequest request) {
        log.info("Received signup request for coach: {}", request.getFirstName());
        return new ResponseEntity<>(
                service.register(request),
                HttpStatus.CREATED
        );
    }

    public ResponseEntity<UpdateCoachResponse> update(UpdateCoachRequest request) {
        return ResponseEntity.ok(service.update(request));
    }

    public ResponseEntity<GetCoachResponse> findByUsername(String username) {
        log.info("Received find by username request for coach: {}", username);
        return ResponseEntity.ok(service.findByUsername(username));
    }


    // todo: use available
    public ResponseEntity<List<CoachDto>> findAvailableCoaches(Boolean available, User user) {
        return ResponseEntity.ok(service.findAvailable(user.getId()));
    }
}


