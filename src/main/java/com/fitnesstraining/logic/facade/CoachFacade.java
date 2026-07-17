package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.abstraction.*;
import com.fitnesstraining.domain.dto.coach.response.CoachDto;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachResponse;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.abstraction.CoachService;
import com.fitnesstraining.logic.abstraction.UserFacade;
import com.fitnesstraining.logic.mapper.CoachMapper;
import com.fitnesstraining.logic.processor.CoachSearcher;
import com.fitnesstraining.logic.processor.CoachUpdater;
import com.fitnesstraining.logic.processor.CoachRegistrar;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class CoachFacade implements UserFacade {

    private final CoachRegistrar registrar;
    private final CoachUpdater updater;
    private final CoachSearcher searcher;

    private final CoachService service;
    private final CoachMapper mapper;


    public RegisterUserResponse register(RegisterUserRequest request) {
        return registrar.register(request);
    }

    @Transactional
    public UpdateCoachResponse update(UpdateUserRequest request) {
        return updater.update(request);
    }

    public GetUserResponse findByUsername(String username) {
        return mapper.toGetCoachResponse((Coach) service.findByUsername(username));
    }

    public List<CoachDto> findAvailableCoaches(Long traineeId) {
        return searcher.findAvailable(traineeId);
    }

    @Transactional
    public void setActive(Activated request) {
        final User user = service.findByUsername(request.getUsername());
        user.setActive(request.getIsActive());
        service.update(user);
    }
}