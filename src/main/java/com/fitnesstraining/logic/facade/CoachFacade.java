package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.logic.abstraction.UserFacade;
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


    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        return signUpProcessor.process(request);
    }

    @Transactional
    public UpdateCoachProfileResponse updateProfile(UpdateUserProfileRequest request) {
        return profileUpdateProcessor.process(request);
    }
}