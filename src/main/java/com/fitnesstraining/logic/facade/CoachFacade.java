package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.abstraction.*;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.abstraction.CoachService;
import com.fitnesstraining.logic.abstraction.UserFacade;
import com.fitnesstraining.utils.CoachMapper;
import com.fitnesstraining.utils.UpdateCoachProfileProcessor;
import com.fitnesstraining.utils.CoachSignUpProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CoachFacade implements UserFacade {

    private final CoachSignUpProcessor signUpProcessor;
    private final UpdateCoachProfileProcessor profileUpdateProcessor;

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

    @Transactional
    public void setActive(Activated request) {
        User user = coachService.findByUsername(request.getUsername());
        user.setActive(request.getIsActive());
        coachService.update(user);
    }
}