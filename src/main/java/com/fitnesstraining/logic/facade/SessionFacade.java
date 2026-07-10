package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.session.SessionRegistrationRequest;
import com.fitnesstraining.utils.SessionCreator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionFacade {

    private final SessionCreator sessionCreator;


    @Transactional
    public void create(SessionRegistrationRequest request) {
        sessionCreator.create(request);
    }
}
