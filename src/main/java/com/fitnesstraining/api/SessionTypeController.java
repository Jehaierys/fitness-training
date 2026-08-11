package com.fitnesstraining.api;

import com.fitnesstraining.api.openapi.SessionTypeControllerApi;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.service.SessionTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SessionTypeController implements SessionTypeControllerApi {

    private final SessionTypeService service;

    public ResponseEntity<List<SessionType>> findAll() {
        log.info("Received request to fetch all session types");
        return ResponseEntity.ok(service.findAll());
    }
}
