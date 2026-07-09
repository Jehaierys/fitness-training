package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.dto.abstraction.UpdateUserProfileResponse;
import com.fitnesstraining.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.dto.abstraction.UserSignUpResponse;

public interface UserFacade {

    UserSignUpResponse signUp(UserSignUpRequest request);

    UpdateUserProfileResponse updateProfile(UpdateUserProfileRequest request);
}
