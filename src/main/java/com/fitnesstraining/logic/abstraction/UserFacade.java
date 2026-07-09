package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileResponse;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;

public interface UserFacade {

    UserSignUpResponse signUp(UserSignUpRequest request);

    UpdateUserProfileResponse updateProfile(UpdateUserProfileRequest request);
}
