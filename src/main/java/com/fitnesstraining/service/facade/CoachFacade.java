package com.fitnesstraining.service.facade;

import com.fitnesstraining.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.service.abstraction.UserFacade;
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