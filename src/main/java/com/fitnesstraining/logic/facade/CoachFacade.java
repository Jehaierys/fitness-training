package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.abstraction.*;
import com.fitnesstraining.domain.dto.coach.response.CoachDto;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.abstraction.CoachService;
import com.fitnesstraining.logic.abstraction.UserFacade;
import com.fitnesstraining.logic.mapper.CoachMapper;
import com.fitnesstraining.logic.processor.CoachSearcher;
import com.fitnesstraining.logic.processor.UpdateCoachProcessor;
import com.fitnesstraining.logic.processor.CoachRegistrationProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class CoachFacade implements UserFacade {

    private final CoachRegistrationProcessor signUpProcessor;
    private final UpdateCoachProcessor profileUpdateProcessor;
    private final CoachSearcher searcher;

    private final CoachService coachService;
    private final CoachMapper mapper;


    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        return signUpProcessor.process(request);
    }

    @Transactional
    public UpdateCoachProfileResponse updateProfile(UpdateUserProfileRequest request) {
        return profileUpdateProcessor.process(request);
    }

    public GetUserResponse findByUsername(String username) {
        return mapper.toGetCoachResponse((Coach) coachService.findByUsername(username));
    }

    public List<CoachDto> findAvailableCoaches(Long traineeId) {
        return searcher.findAvailable(traineeId);
    }

    @Transactional
    public void setActive(Activated request) {
        User user = coachService.findByUsername(request.getUsername());
        user.setActive(request.getIsActive());
        coachService.update(user);
    }
}